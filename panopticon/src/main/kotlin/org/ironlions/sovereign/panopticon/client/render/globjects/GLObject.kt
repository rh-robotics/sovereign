package org.ironlions.sovereign.panopticon.client.render.globjects

import java.io.Closeable

/** A buffer object, of some sort. */
interface GLObject : Closeable {
    /** Bind the buffer object. */
    fun bind()

    /** Unbind the buffer object. */
    fun unbind()
}