package org.ironlions.sovereign.panopticon.client.render.buffers

import java.io.Closeable

/** A buffer object, of some sort. */
interface BufferObject : Closeable {
    /** Bind the buffer object. */
    fun bind()

    /** Unbind the buffer object. */
    fun unbind()
}