package org.ironlions.sovereign.panopticon.client.render

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
 * @param position The position of the camera.
 * @param lookAt What the camera is looking at.
 * @param phi The amount the camera is rotated around the origin on the x-axis.
 * @param theta The amount of camera is rotated around the origin on the y-axis.
 * @param radius The distance of the camera from the origin.
 * @param cameraSpeed The speed at which the camera moves per frame.
 * @param mouseSensitivity The amount at which to multiply camera input.
 */
class Camera(
    framebufferWidth: Int,
    framebufferHeight: Int,
    var position: Vec3 = Vec3(0, 0, 2),
    private var lookAt: Vec3 = Vec3(0),
    private var phi: Float = -glm.PIf / 4,
    private var theta: Float = -1.0f,
    private var radius: Float = 5.0f,
    private val cameraSpeed: Float = 0.1f,
    private val mouseSensitivity: Float = 0.01f,
) : EventReceiver {
    private var up: Vec3 = Vec3(0.0f, 1.0f, 0.0f)

    /** The projection matrix to use. */
    var projectionMatrix: Mat4 = glm.perspective(
        glm.radians(45.0f),
        framebufferWidth.toFloat() / framebufferHeight,
        0.1f,
        5000.0f
    )

    /** The framebuffer to use. */
    var framebuffer: Framebuffer = Framebuffer(framebufferWidth, framebufferHeight)

    init {
        calculateCameraVectors()
    }

    /**
     * Get the view matrix from all the vectors.
     */
    fun getViewMatrix(): Mat4 {
        return glm.lookAt(position, lookAt, up)
    }

    /** Recalculate all the camera vectors. */
    private fun calculateCameraVectors() {
        position = Vec3(
            lookAt.x + radius * sin(theta) * cos(phi),
            lookAt.y + radius * cos(theta),
            lookAt.z + radius * sin(theta) * sin(phi)
        )
    }

    /**
     * Process any nascent mouse movement.
     *
     * @param xOffset The amount the mouse has moved in the X direction since the last frame.
     * @param yOffset The amount the mouse has moved in the Y direction since the last frame.
     */
    private fun processMouseInput(window: Long, xOffset: Float, yOffset: Float) {
        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_RIGHT) == GLFW_PRESS) {
            phi += xOffset * mouseSensitivity
            theta -= yOffset * mouseSensitivity
            theta = glm.clamp(glm.abs(theta), 0.1f, glm.PIf) * glm.sign(theta)

            calculateCameraVectors()
        }
    }

    /** Calculate the projection matrix.
     *
     * @param width The width of the framebuffer.
     * @param height The height of the framebuffer.
     */
    private fun onFramebufferResize(width: Float, height: Float) {
        projectionMatrix = glm.perspective(
            glm.radians(45.0f),
            width / height,
            0.1f,
            100.0f
        )
    }

    override fun onEvent(event: Event) = when (event) {
        is Event.Mouse -> processMouseInput(event.window, event.xOffset, event.yOffset)
        is Event.FramebufferResize -> onFramebufferResize(
            event.width.toFloat(),
            event.height.toFloat()
        )

        else -> {}
    }
}