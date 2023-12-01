package org.ironlions.panopticon

import org.ironlions.panopticon.monitor.Watcher
import org.ironlions.panopticon.monitor.Monitored
import org.ironlions.common.panopticon.proto.Packet
import org.ironlions.common.panopticon.proto.Thing
import java.time.Instant
import java.util.UUID

class Aggregator {
    private val packets: MutableList<Packet> = mutableListOf()
    private val monitoring: MutableMap<UUID, Watcher> = mutableMapOf()

    /**
     * Consider a single class for spying on. The class in question must be marked with [Monitored].
     *
     * @param monitored The class to monitor.
     */
    fun monitor(monitored: Watcher) = apply { monitoring[UUID.randomUUID()] = monitored }

    /**
     * Record the current state of all monitored objects for later serialization.
     */
    fun record() {
        val changed = monitoring.filter { it.value.isDirty() }
        val things = changed.map { Thing(uuid = it.key.toString()) }

        packets.add(
            Packet(
                timestamp = Instant.now(), things = things
            )
        )
    }
}