package org.ironlions.sovereign.panopticon.client.data

import java.nio.file.Path
import java.time.Instant
import org.ironlions.sovereign.common.proto.environment.Environment
import org.ironlions.sovereign.common.proto.historical.Historical

class RecordDataSource(source: Path) : DataSource() {
    private val packets: List<Pair<Instant, Environment.Packet>>

    init {
        val record = Historical.Record.parseFrom(source.toFile().inputStream())
        val sorted = record.packetsList.sortedWith(
            compareBy({ it.timestamp.seconds }, { it.timestamp.nanos })
        )

        packets = sorted.map {
            Pair(
                Instant.ofEpochSecond(
                    it.timestamp.seconds, it.timestamp.nanos.toLong()
                ), it
            )
        }
    }

    override fun listenFrame() {}

    override fun since(time: Instant): List<Environment.Packet>? {
        packets.forEachIndexed { i, packet ->
            if (packet.first.isAfter(time)) return packets.subList(0, i).map { it.second }
        }

        return null
    }

    override fun lastAvailable(): Instant = Instant.ofEpochSecond(
        packets.last().second.timestamp.seconds, packets.last().second.timestamp.nanos.toLong()
    )
}