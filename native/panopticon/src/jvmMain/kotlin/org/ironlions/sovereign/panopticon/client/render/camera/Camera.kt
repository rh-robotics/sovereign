package org.ironlions.sovereign.panopticon.client.render.camera

import glm_.glm
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import org.ironlions.sovereign.panopticon.client.event.Event
import org.ironlions.sovereign.panopticon.client.event.EventReceiver
import org.ironlions.sovereign.panopticon.client.render.globjects.Framebuffer
import org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT
import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.glfwGetMouseButton
import kotlin.math.cos
import kotlin.math.sin

/**
 * A camera from which to render a scene.
 *
 * @param framebufferWidth The width of the framebuffer to render to.
 * @param framebufferHeight The height of the framebuffer to render to.
 */
abstract class Camera(
    framebufferWidth: Int,
    framebufferHeight: Int,
    public var position: Vec3,
) : EventReceiver {
    var framebuffer: Framebuffer = Framebuffer(framebufferWidth, framebufferHeight)

    /** The projection matrix to use. */
    var projectionMatrix: Mat4 = glm.perspective(
        glm.radians(45.0f),
        framebufferWidth.toFloat() / framebufferHeight,
        0.1f,
        5000.0f
    )

    /** Get the view matrix from all the vectors. */
    abstract fun getViewMatrix(): Mat4

    /** Recalculate all the camera vectors. */
    abstract fun calculateCameraVectors()
}
