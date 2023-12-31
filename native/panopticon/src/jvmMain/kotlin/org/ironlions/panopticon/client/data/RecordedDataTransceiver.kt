package org.ironlions.panopticon.client.data

import kotlinx.serialization.json.Json
import org.ironlions.common.panopticon.proto.NewComponentRequest
import org.ironlions.common.panopticon.proto.RemoveComponentRequest
import org.ironlions.common.panopticon.proto.PingRequest
import org.ironlions.common.panopticon.proto.SetupRequest
import java.nio.file.Path
import java.time.Instant
import org.ironlions.common.panopticon.proto.UpdatePropertiesRequest
import org.ironlions.panopticon.client.ecs.Component
import java.lang.StringBuilder
import java.util.Stack
import java.util.TreeMap

class RecordedDataTransceiver(source: Path) : DataTransceiver() {
    private val commands: TreeMap<Instant, CommandWrapper>
    private var cursor: Instant
    private val componentStacks: MutableMap<String, Stack<Component>> = mutableMapOf()

    /* Transplanted code that may be useful.
     *
     * ```kotlin
     * val component = Component(scene)
     * val region = it.properties.filterIsInstance<Component.Property.Region>().first().sensibilitize()
     * val color = it.properties.filterIsInstance<Component.Property.Color>().first()
     *
     * component.ecsComponents.clear()
     * component.attachEcsComponent(
     *     Mesh::class, Mesh(
     *          component,
     *          Mat4(1),
     *          program,
     *          BoundingBox.vertices(region, color),
     *          BoundingBox.indices,
     *          type = GL_LINES,
     *     )
     * )
     *
     * scene[it.uuid.toString()] = component
     *
     * ```
     */

    init {
        val parsedCommandWrappers =
            Json.decodeFromString<List<CommandWrapper>>(source.toFile().readText())
        val sorted = parsedCommandWrappers.sortedWith(
            compareBy({ it.timestamp.epochSecond }, { it.timestamp.nano })
        )

        commands = TreeMap(sorted.associateBy {
            Instant.ofEpochSecond(
                it.timestamp.epochSecond, it.timestamp.nano.toLong()
            )
        })

        cursor = commands.firstKey()
        pushPacket()
    }

    override fun listenFrame() {}

    override fun next() {
        cursor = commands.navigableKeySet().higher(cursor) ?: return
        pushPacket()
    }

    override fun prior() {
        popPacket()
        cursor = commands.navigableKeySet().lower(cursor) ?: return
    }

    private fun pushPacket() {
        // TODO: Fill this in.
        when (val underlying = commands[cursor]!!.command.underlying) {
            is PingRequest -> {}
            is SetupRequest -> {}
            is NewComponentRequest -> {}
            is RemoveComponentRequest -> {}
            is UpdatePropertiesRequest -> {}
            else -> throw RuntimeException("Unknown message type ${underlying::class.simpleName}")
        }

        /* commands[cursor]!!.things.forEach {
            val stack = thingStacks.getOrPut(it.uuid) { Stack() }

            Logging.logger.debug { "Applying thing '${it.uuid}' v${stack.size + 1}" }
            stack.push(it)
        } */
    }

    private fun popPacket() {
        // TODO: Fill this in.
        when (val underlying = commands[cursor]!!.command.underlying) {
            is PingRequest -> {}
            is SetupRequest -> {}
            is NewComponentRequest -> {}
            is RemoveComponentRequest -> {}
            is UpdatePropertiesRequest -> {}
            else -> throw RuntimeException("Unknown message type ${underlying::class.simpleName}")
        }

        /* commands[cursor]!!.things.forEach {
            val stack = thingStacks[it.uuid]!!

            Logging.logger.debug { "Rolling back thing '${it.uuid}' v${stack.size}." }
            stack.pop()
        } */
    }

    override fun components(): List<Component> =
        componentStacks.filter { it.value.isNotEmpty() }.map { it.value.peek() }

    override fun size(): Int = commands.size

    override fun toString(): String {
        val builder = StringBuilder()

        commands.forEach { builder.appendLine("packet ${it.key}: ${it.value.hashCode()}") }
        components().forEach { builder.appendLine("thing ${it.uuid}: ${it.hashCode()} ") }

        return builder.toString()
    }
}