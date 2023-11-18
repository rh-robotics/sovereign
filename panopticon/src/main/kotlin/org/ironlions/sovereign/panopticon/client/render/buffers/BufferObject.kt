package org.ironlions.sovereign.panopticon.client.render.buffers

/** A buffer object, of some sort. */
interface BufferObject {
    /** Bind the buffer object. */
    fun bind()

    /** Unbind the buffer object. */
    fun unbind()

    /** Destroy. */
    fun destroy()
}