package org.ironlions.panopticon.client.ui

import glm_.mat4x4.Mat4
import org.ironlions.common.components.Component
import org.ironlions.panopticon.client.ClientApplication
import org.ironlions.panopticon.client.ecs.Entity
import org.ironlions.panopticon.client.ecs.Scene
import org.ironlions.panopticon.client.ecs.components.Mesh
import org.ironlions.panopticon.client.render.Renderer
import org.ironlions.panopticon.client.event.Event
import org.ironlions.panopticon.client.event.EventReceiver
import org.ironlions.panopticon.client.render.geometry.BoundingBox
import org.ironlions.panopticon.client.render.shader.Program
import org.ironlions.panopticon.client.util.IOUtil
import org.ironlions.panopticon.client.util.sensibilitize
import org.lwjgl.opengl.GL41.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL41.GL_DEPTH_BUFFER_BIT
import org.lwjgl.opengl.GL41.GL_LINES
import org.lwjgl.opengl.GL41.glClear
import org.lwjgl.opengl.GL41.glViewport

class GraphicsScene : Window("Viewer"), EventReceiver {
    private lateinit var program: Program
    private val scene: Scene = Scene(
        name = "main",
    )

    override fun init(renderer: Renderer) {
        eventDispatcher.subscribe(this as EventReceiver, listOf(Event.FramebufferResize::class))
        eventDispatcher.subscribe(
            renderer.activeCamera as EventReceiver, listOf(Event.FramebufferResize::class)
        )

        program = Program(
            name = "main",
            vertexSource = IOUtil.ioResourceToByteBuffer("shader.vert", 4096),
            fragmentSource = IOUtil.ioResourceToByteBuffer("shader.frag", 4096)
        )
    }

    override fun content(renderer: Renderer) {
        renderer.activeCamera.framebuffer.imgui()

        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glViewport(
            0, 0, availableSpace!!.x.toInt(), availableSpace!!.y.toInt()
        )
        eventDispatcher.broadcastToSubscribers(Event.Frame(renderer.window, renderer.deltaTime))

        // TODO: Mesh caching.
        scene.components.clear()
        (ClientApplication.windows[Inspector::class]!! as Inspector).dataTransceiver?.let { data ->
            data.components().filterIsInstance<Component.Concrete>().forEach {
                val entity = Entity(scene)

                entity.components.clear()
                entity.attachComponent(
                    Mesh::class, Mesh(
                        entity,
                        Mat4(1),
                        program,
                        BoundingBox.vertices(it.region.sensibilitize(), it.color),
                        BoundingBox.indices,
                        type = GL_LINES,
                    )
                )

                scene[it.uuid.toString()] = entity
            }
        }

        scene.draw(renderer)
    }

    override fun onEvent(event: Event) = when (event) {
        is Event.FramebufferResize -> event.framebuffer.resize(event.width, event.height)

        else -> {}
    }

    override fun destroy() = scene.destroy()
}
