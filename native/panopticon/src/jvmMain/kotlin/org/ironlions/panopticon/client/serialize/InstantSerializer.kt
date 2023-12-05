package org.ironlions.panopticon.client.serialize

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import java.time.Instant

/** Serializes an object in the format {seconds, nanos} into an [Instant]. */
object InstantSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Timestamp") {
        element<Long>("seconds")
        element<Int>("nanos")
    }

    override fun serialize(encoder: Encoder, value: Instant) =
        encoder.encodeStructure(descriptor) {
            encodeLongElement(descriptor, 0, value.epochSecond)
            encodeIntElement(descriptor, 1, value.nano)
        }

    override fun deserialize(decoder: Decoder): Instant =
        decoder.decodeStructure(descriptor) {
            var seconds: Long = -1
            var nanos: Long = -1

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> seconds = decodeLongElement(descriptor, 0)
                    1 -> nanos = decodeLongElement(descriptor, 1)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }

            require(seconds != -1L && nanos != -1L)
            Instant.ofEpochSecond(seconds, nanos)
        }
}