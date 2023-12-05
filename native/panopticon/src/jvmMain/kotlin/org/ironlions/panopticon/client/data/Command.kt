package org.ironlions.panopticon.client.data

import com.squareup.wire.Message
import kotlinx.serialization.Serializable
import java.time.Instant
import org.ironlions.panopticon.client.serialize.InstantSerializer
import org.ironlions.panopticon.client.serialize.CommandSerializer

@Serializable
data class CommandWrapper(
    @Serializable(with = InstantSerializer::class) val timestamp: Instant,
    @Serializable(with = CommandSerializer::class) val command: Command
)

data class Command(
    val underlying: Message<*, *>
)
