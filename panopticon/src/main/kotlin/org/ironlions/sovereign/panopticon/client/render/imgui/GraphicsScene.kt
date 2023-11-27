package org.ironlions.sovereign.panopticon.client.render.imgui

import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import org.ironlions.sovereign.panopticon.client.ecs.Entity
import org.ironlions.sovereign.panopticon.client.ecs.Scene
import org.ironlions.sovereign.panopticon.client.ecs.components.Mesh
import org.ironlions.sovereign.panopticon.client.render.Renderer
import org.ironlions.sovereign.panopticon.client.render.event.Event
import org.ironlions.sovereign.panopticon.client.render.event.EventReceiver
import org.ironlions.sovereign.panopticon.client.render.geometry.Vertex
import org.ironlions.sovereign.panopticon.client.render.shader.Program
import org.ironlions.sovereign.panopticon.client.util.IOUtil
import org.lwjgl.opengl.GL41.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL41.GL_DEPTH_BUFFER_BIT
import org.lwjgl.opengl.GL41.glClear
import org.lwjgl.opengl.GL41.glViewport

class GraphicsScene : Window("Viewer"), EventReceiver {
    private val vertices = listOf(
        // Face 1 (closest to camera)
        Vertex(
            Vec3(0.5, 0.5, 0.5), Vec3(1, 0, 0), Vec3(0.0, 0.0, 1.0)
        ), Vertex(
            Vec3(0.5, -0.5, 0.5), Vec3(0, 1, 0), Vec3(0.0, 0.0, 1.0)
        ), Vertex(
            Vec3(-0.5, -0.5, 0.5), Vec3(0, 0, 1), Vec3(0.0, 0.0, 1.0)
        ), Vertex(
            Vec3(-0.5, 0.5, 0.5), Vec3(0, 0, 0), Vec3(0.0, 0.0, 1.0)
        ),

        // Face 2 (top)
        Vertex(
            Vec3(0.5, 0.5, 0.5), Vec3(1, 0, 0), Vec3(0.0, 1.0, 0.0)
        ), Vertex(
            Vec3(-0.5, 0.5, 0.5), Vec3(1, 0, 0), Vec3(0.0, 1.0, 0.0)
        ), Vertex(
            Vec3(-0.5, 0.5, -0.5), Vec3(1, 0, 0), Vec3(0.0, 1.0, 0.0)
        ), Vertex(
            Vec3(0.5, 0.5, -0.5), Vec3(1, 0, 0), Vec3(0.0, 1.0, 0.0)
        ),

        // Face 3 (bottom)
        Vertex(
            Vec3(0.5, -0.5, 0.5), Vec3(1, 0, 0), Vec3(0.0, -1.0, 0.0)
        ), Vertex(
            Vec3(-0.5, -0.5, 0.5), Vec3(1, 0, 0), Vec3(0.0, -1.0, 0.0)
        ), Vertex(
            Vec3(-0.5, -0.5, -0.5), Vec3(1, 0, 0), Vec3(0.0, -1.0, 0.0)
        ), Vertex(
            Vec3(0.5, -0.5, -0.5), Vec3(1, 0, 0), Vec3(0.0, -1.0, 0.0)
        ),

        // Face 4 (left)
        Vertex(
            Vec3(-0.5, 0.5, -0.5), Vec3(1, 0, 0), Vec3(-1.0, 0.0, 0.0)
        ), Vertex(
            Vec3(-0.5, -0.5, -0.5), Vec3(1, 0, 0), Vec3(-1.0, 0.0, 0.0)
        ), Vertex(
            Vec3(-0.5, -0.5, 0.5), Vec3(1, 0, 0), Vec3(-1.0, 0.0, 0.0)
        ), Vertex(
            Vec3(-0.5, 0.5, 0.5), Vec3(1, 0, 0), Vec3(-1.0, 0.0, 0.0)
        ),

        // Face 5 (right)
        Vertex(
            Vec3(0.5, 0.5, -0.5), Vec3(1, 0, 0), Vec3(1.0, 0.0, 0.0)
        ), Vertex(
            Vec3(0.5, -0.5, -0.5), Vec3(1, 0, 0), Vec3(1.0, 0.0, 0.0)
        ), Vertex(
            Vec3(0.5, -0.5, 0.5), Vec3(1, 0, 0), Vec3(1.0, 0.0, 0.0)
        ), Vertex(
            Vec3(0.5, 0.5, 0.5), Vec3(1, 0, 0), Vec3(1.0, 0.0, 0.0)
        ),

        // Face 6 (farthest from camera)
        Vertex(
            Vec3(-0.5, 0.5, -0.5), Vec3(1, 0, 0), Vec3(0.0, 0.0, -1.0)
        ), Vertex(
            Vec3(-0.5, -0.5, -0.5), Vec3(1, 0, 0), Vec3(0.0, 0.0, -1.0)
        ), Vertex(
            Vec3(0.5, -0.5, -0.5), Vec3(1, 0, 0), Vec3(0.0, 0.0, -1.0)
        ), Vertex(
            Vec3(0.5, 0.5, -0.5), Vec3(1, 0, 0), Vec3(0.0, 0.0, -1.0)
        )
    )
    private val indices = listOf(
        // Face 1
        3, 1, 0,
        3, 2, 1,

        // Face 2
        3 + 4, 1 + 4, 0 + 4,
        3 + 4, 2 + 4, 1 + 4,

        // Face 3
        0 + 8, 1 + 8, 3 + 8,
        1 + 8, 2 + 8, 3 + 8,

        // Face 4
        0 + 12, 1 + 12, 3 + 12,
        1 + 12, 2 + 12, 3 + 12,

        // Face 5
        3 + 16, 1 + 16, 0 + 16,
        3 + 16, 2 + 16, 1 + 16,

        // Face 6
        3 + 20, 1 + 20, 0 + 20,
        3 + 20, 2 + 20, 1 + 20,
    )

    private val scene: Scene = Scene(
        name = "main",
    )

    override fun init(renderer: Renderer) {
        eventDispatcher.subscribe(this as EventReceiver, listOf(Event.FramebufferResize::class))
        eventDispatcher.subscribe(
            renderer.activeCamera as EventReceiver, listOf(Event.FramebufferResize::class)
        )

        val entity = Entity(scene)
        entity.addComponent(
            Mesh::class, Mat4(1), Program(
                name = "main",
                vertexSource = IOUtil.ioResourceToByteBuffer("shader.vert", 4096),
                fragmentSource = IOUtil.ioResourceToByteBuffer("shader.frag", 4096)
            ), vertices, indices
        )
        scene.add(entity)
    }

    override fun content(renderer: Renderer) {
        renderer.activeCamera.framebuffer.imgui()

        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glViewport(
            0,
            0,
            availableSpace!!.x.toInt(),
            availableSpace!!.y.toInt()
        )
        eventDispatcher.broadcastToSubscribers(Event.Frame(renderer.window, renderer.deltaTime))

        scene.draw(renderer)
    }

    override fun onEvent(event: Event) = when (event) {
        is Event.FramebufferResize -> event.framebuffer.resize(event.width, event.height)
        else -> {}
    }

    override fun destroy() = scene.destroy()
}