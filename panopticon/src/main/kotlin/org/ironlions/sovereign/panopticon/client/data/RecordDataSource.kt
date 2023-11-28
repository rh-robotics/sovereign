package org.ironlions.sovereign.panopticon.client.data

import org.ironlions.sovereign.panopticon.client.Logging
import java.nio.file.Path
import java.time.Instant
import org.ironlions.sovereign.proto.environment.Environment
import java.lang.StringBuilder
import java.time.Duration
import java.util.Stack
import java.util.TreeMap
import java.util.TreeSet

class RecordDataSource(source: Path) : DataSource() {
    private val packets: TreeMap<Instant, Environment.Packet>
    private var cursor: Instant
    val thingStacks: MutableMap<String, Stack<Environment.Thing>> = mutableMapOf()

    init {
        val record = Environment.Packets.parseFrom(source.toFile().inputStream())
        val sorted = record.packetsList.sortedWith(
            compareBy({ it.timestamp.seconds }, { it.timestamp.nanos })
        )

        packets = TreeMap(sorted.associateBy {
            Instant.ofEpochSecond(
                it.timestamp.seconds, it.timestamp.nanos.toLong()
            )
        })

        cursor = packets.firstKey()
        pushPacket()
    }

    override fun listenFrame() {}

    override fun next() {
        cursor = packets.navigableKeySet().higher(cursor) ?: return
        pushPacket()
    }

    override fun prior() {
        popPacket()
        cursor = packets.navigableKeySet().lower(cursor) ?: return
    }

    private fun pushPacket() {
        packets[cursor]!!.thingsList.forEach {
            val stack = thingStacks.getOrPut(it.uuid) { Stack() }

            Logging.logger.debug { "Applying thing '${it.uuid}' v${stack.size+1}" }
            stack.push(it)
        }
    }

    private fun popPacket() {
        packets[cursor]!!.thingsList.forEach {
            val stack = thingStacks[it.uuid]!!

            Logging.logger.debug { "Rolling back thing '${it.uuid}' v${stack.size}." }
            stack.pop()
        }
    }

    override fun things(): List<Environment.Thing> =
        thingStacks.filter { it.value.isNotEmpty() }.map { it.value.peek() }

    override fun size(): Int = packets.size

    override fun toString(): String {
        val builder = StringBuilder()

        packets.forEach { builder.appendLine("packet ${it.key}: ${it.value.hashCode()}") }
        things().forEach { builder.appendLine("thing ${it.uuid}: ${it.hashCode()} ") }

        return builder.toString()
    }
}