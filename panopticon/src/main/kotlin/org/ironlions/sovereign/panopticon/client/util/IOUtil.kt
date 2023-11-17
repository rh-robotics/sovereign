package org.ironlions.sovereign.panopticon.client.util

import org.lwjgl.BufferUtils
import org.lwjgl.system.MemoryUtil
import java.io.IOException
import java.net.URL
import java.nio.ByteBuffer
import java.nio.channels.Channels
import java.nio.file.Files
import java.nio.file.Paths

/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */

object IOUtil {
    private fun resizeBuffer(buffer: ByteBuffer, newCapacity: Int): ByteBuffer {
        val newBuffer = BufferUtils.createByteBuffer(newCapacity)
        buffer.flip()
        newBuffer.put(buffer)
        return newBuffer
    }

    /**
     * Reads the specified resource and returns the raw data as a ByteBuffer.
     *
     * @param resource   the resource to read
     * @param bufferSize the initial buffer size
     *
     * @return the resource data
     *
     * @throws IOException if an IO error occurs
     */
    @Suppress("ControlFlowWithEmptyBody")
    fun ioResourceToByteBuffer(resource: String, bufferSize: Int = 4096): ByteBuffer {
        var buffer: ByteBuffer? = null
        val path = if (resource.startsWith("http")) null else Paths.get(resource)

        if (path != null && Files.isReadable(path)) {
            Files.newByteChannel(path).use { fc ->
                buffer = BufferUtils.createByteBuffer(fc.size().toInt() + 1)
                while (fc.read(buffer) != -1);
            }
        } else {
            if (resource.startsWith("http")) URL(resource).openStream() else IOUtil::class.java.classLoader.getResourceAsStream(
                resource
            ).use { source ->
                Channels.newChannel(source!!).use { rbc ->
                    buffer = BufferUtils.createByteBuffer(bufferSize)

                    while (true) {
                        val bytes = rbc.read(buffer)

                        if (bytes == -1) {
                            break
                        }

                        if (buffer!!.remaining() == 0) {
                            buffer = resizeBuffer(buffer!!, buffer!!.capacity() * 3 / 2) // 50%
                        }
                    }
                }
            }
        }

        buffer!!.flip()
        return MemoryUtil.memSlice(buffer!!)
    }
}