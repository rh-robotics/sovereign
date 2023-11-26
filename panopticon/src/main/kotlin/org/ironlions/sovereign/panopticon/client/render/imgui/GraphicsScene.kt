package org.ironlions.sovereign.panopticon.client.render.imgui

import org.ironlions.sovereign.panopticon.client.render.Renderer
import org.ironlions.sovereign.panopticon.client.render.event.Event
import org.ironlions.sovereign.panopticon.client.render.event.EventReceiver

class GraphicsScene : Window("Ontomorphic Phenomenographical Display"), EventReceiver {
    override fun init(renderer: Renderer) {
        eventDispatcher.subscribe(this as EventReceiver, listOf(Event.FramebufferResize::class))
        eventDispatcher.subscribe(
            renderer.activeCamera as EventReceiver, listOf(Event.FramebufferResize::class)
        )
    }

    override fun content(renderer: Renderer) = renderer.activeCamera.framebuffer.imgui()

    override fun onEvent(event: Event) = when (event) {
        is Event.FramebufferResize -> event.framebuffer.resize(event.width, event.height)
        else -> {}
    }
}