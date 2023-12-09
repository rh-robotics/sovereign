package org.ironlions.panopticon.client.serialize

import com.squareup.wire.Message
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import org.ironlions.panopticon.client.data.Command
import java.util.Base64

/** Serializes an object in the format {type, data} into an [Command]. */
object CommandSerializer : KSerializer<Command> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Command") {
        element<String>("type")
        element<String>("data")
    }

    override fun serialize(encoder: Encoder, value: Command) = TODO()

    override fun deserialize(decoder: Decoder): Command = decoder.decodeStructure(descriptor) {
        var type: String? = null
        var data: String? = null

        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> type = decodeStringElement(descriptor, 0)
                1 -> data = decodeStringElement(descriptor, 1)
                CompositeDecoder.DECODE_DONE -> break
                else -> error("Unexpected index: $index")
            }
        }

        require(type != null && data != null)

        val className = "org.ironlions.common.panopticon.proto.$type"
        val loadedClass = Class.forName(className)
        val instance = loadedClass.getDeclaredConstructor().newInstance() as Message<*, *>

        instance.adapter.decode(Base64.getDecoder().decode(data))

        Command(underlying = instance)
    }
}