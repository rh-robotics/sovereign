package org.ironlions.sovereign.panopticon.client.render

import glm_.glm
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import org.ironlions.sovereign.panopticon.client.render.event.Event
import org.ironlions.sovereign.panopticon.client.render.event.EventReceiver
import org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT
import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.glfwGetMouseButton
import kotlin.math.cos
import kotlin.math.sin

/**
 * A camera from which to render a scene.
 *
 * @param position The position of the camera.
 * @param up The up vector.
 * @param yaw The amount of yaw.
 * @param pitch The amount of pitch.
 * @param cameraSpeed The speed at which the camera moves per frame.
 * @param mouseSensitivity The amount at which to multiply camera input.
 */
class Camera(
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
    var projectionMatrix: Mat4 = glm.perspective(glm.radians(45.0f), 1.7142857f, 0.1f, 100.0f)

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
        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {
            phi += xOffset * mouseSensitivity
            theta -= yOffset * mouseSensitivity
            theta = glm.clamp(glm.abs(theta), 0.1f, glm.PIf) * glm.sign(theta)

            // if (glm.dot(-glm.transpose(getViewMatrix())[2], Vec4(up, 0)) * glm.sign(yOffset) > 0.99f)

            calculateCameraVectors()
        }
    }

    /** Calculate the projection matrix.
     *
     * @param width The width of the framebuffer.
     * @param height The height of the framebuffer.
     */
    private fun calculateProjectionMatrix(width: Float, height: Float) {
        projectionMatrix = glm.perspective(
            glm.radians(45.0f),
            width / height,
            0.1f,
            100.0f
        )
    }

    override fun onEvent(event: Event) = when (event) {
        is Event.Mouse -> processMouseInput(event.window, event.xOffset, event.yOffset)
        is Event.FramebufferResize -> calculateProjectionMatrix(event.width.toFloat(), event.height.toFloat())
        else -> {}
    }
}