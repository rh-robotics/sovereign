package org.ironlions.panopticon.client.data

import org.ironlions.panopticon.client.Logging
import java.nio.file.Path
import java.time.Instant
import org.ironlions.proto.panopticon.environment.Thing
import org.ironlions.proto.panopticon.environment.Packet
import org.ironlions.proto.panopticon.environment.Packets
import java.lang.StringBuilder
import java.util.Stack
import java.util.TreeMap

class RecordDataSource(source: Path) : DataSource() {
    private val packets: TreeMap<Instant, Packet>
    private var cursor: Instant
    val thingStacks: MutableMap<String, Stack<Thing>> = mutableMapOf()

    init {
        val record = Packets.ADAPTER.decode(source.toFile().inputStream())
        val sorted = record.packets.sortedWith(
            compareBy({ it.timestamp!!.epochSecond }, { it.timestamp!!.nano })
        )

        packets = TreeMap(sorted.associateBy {
            Instant.ofEpochSecond(
                it.timestamp!!.epochSecond, it.timestamp!!.nano.toLong()
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
        packets[cursor]!!.things.forEach {
            val stack = thingStacks.getOrPut(it.uuid) { Stack() }

            Logging.logger.debug { "Applying thing '${it.uuid}' v${stack.size + 1}" }
            stack.push(it)
        }
    }

    private fun popPacket() {
        packets[cursor]!!.things.forEach {
            val stack = thingStacks[it.uuid]!!

            Logging.logger.debug { "Rolling back thing '${it.uuid}' v${stack.size}." }
            stack.pop()
        }
    }

    override fun things(): List<Thing> =
        thingStacks.filter { it.value.isNotEmpty() }.map { it.value.peek() }

    override fun size(): Int = packets.size

    override fun toString(): String {
        val builder = StringBuilder()

        packets.forEach { builder.appendLine("packet ${it.key}: ${it.value.hashCode()}") }
        things().forEach { builder.appendLine("thing ${it.uuid}: ${it.hashCode()} ") }

        return builder.toString()
    }
}