package org.ironlions.panopticon.client.event

import org.ironlions.panopticon.client.render.globjects.Framebuffer

/** A subscribable event type. */
open class Event {
    /**
     * A new frame is about to be rendered.
     *
     * @param window The window that experienced the event.
     * @param deltaTime The time since the last frame.
     */
    class Frame(val window: Long, val deltaTime: Float) : Event()

    /**
     * A mouse input event occurred.
     *
     * @param xOffset The X offset of the mouse since the last read.
     * @param yOffset The y offset of the mouse since the last read.
     */
    class Mouse(val window: Long, val xOffset: Float, val yOffset: Float) : Event()

    /**
     * A framebuffer resize event occurred.
     *
     * @param framebuffer The framebuffer that experienced the resize.
     * @param width The new framebuffer width.
     * @param height The new framebuffer height
     */
    class FramebufferResize(val framebuffer: Framebuffer, val width: Int, val height: Int) : Event()
}