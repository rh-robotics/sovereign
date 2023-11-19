package org.ironlions.sovereign.panopticon.client.render.event

/** A subscribable event type. */
open class Event {
    /** A new frame is about to be rendered. */
    class Frame(val window: Long, val deltaTime: Float) : Event()

    /** A mouse input event occurred. */
    class Mouse(val xOffset: Float, val yOffset: Float) : Event()

    /** A framebuffer resize event occurred. */
    class FramebufferResize(val width: Int, val height: Int) : Event()
}