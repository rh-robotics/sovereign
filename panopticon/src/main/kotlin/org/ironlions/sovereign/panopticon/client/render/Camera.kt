package org.ironlions.sovereign.panopticon.client.render

import glm_.glm
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import org.ironlions.sovereign.panopticon.client.render.event.Event
import org.ironlions.sovereign.panopticon.client.render.event.EventReceiver
import org.lwjgl.glfw.GLFW.GLFW_KEY_A
import org.lwjgl.glfw.GLFW.GLFW_KEY_D
import org.lwjgl.glfw.GLFW.GLFW_KEY_S
import org.lwjgl.glfw.GLFW.GLFW_KEY_W
import org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE
import org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT
import org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT
import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.glfwGetKey
import org.lwjgl.glfw.GLFW.glfwGetMouseButton
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
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
    val position: Vec3 = Vec3(0, 0, 2),
    private var up: Vec3 = Vec3(0.0f, 1.0f, 0.0f),
    private var yaw: Float = -90.0f,
    private var pitch: Float = 0f,
    private val cameraSpeed: Float = 0.1f,
    private val mouseSensitivity: Float = 0.1f,
) : EventReceiver {
    private var front: Vec3 = Vec3(0.0f, 0.0f, -1.0f)
    private var worldUp: Vec3 = Vec3(up.x, up.y, up.z)
    private lateinit var right: Vec3

    /** The projection matrix to use. */
    var projectionMatrix: Mat4 = glm.perspective(glm.radians(45.0f), 1.7142857f, 0.1f, 100.0f)

    init {
        calculateCameraVectors()
    }

    /**
     * Get the view matrix from all the vectors.
     */
    fun getViewMatrix(): Mat4 {
        return glm.lookAt(position, front + position, up)
    }

    /** Recalculate all the camera vectors. */
    private fun calculateCameraVectors() {
        front.x = cos(glm.radians(yaw)) * cos(glm.radians(pitch))
        front.y = sin(glm.radians(pitch))
        front.z = sin(glm.radians(yaw)) * cos(glm.radians(pitch))
        front = glm.normalize(front)
        right = glm.normalize(glm.cross(front, worldUp))
        up = glm.normalize(glm.cross(right, front))
    }

    /** Process some keyword input.
     *
     * @param window The window receiving the event.
     * @param deltaTime The time since the last frame.
     */
    private fun processKeyboardInput(window: Long, deltaTime: Float) {
        val velocity = cameraSpeed * deltaTime

        if (GLFW_PRESS == glfwGetKey(window, GLFW_KEY_W)) {
            position += front * velocity
        }

        if (GLFW_PRESS == glfwGetKey(window, GLFW_KEY_S)) {
            position -= front * velocity
        }

        if (GLFW_PRESS == glfwGetKey(window, GLFW_KEY_D)) {
            position += right * velocity
        }

        if (GLFW_PRESS == glfwGetKey(window, GLFW_KEY_A)) {
            position -= right * velocity
        }

        if (GLFW_PRESS == glfwGetKey(window, GLFW_KEY_SPACE)) {
            position += up * velocity
        }

        if (GLFW_PRESS == glfwGetKey(window, GLFW_KEY_LEFT_SHIFT)) {
            position -= up * velocity
        }
    }

    /**
     * Process any nascent mouse movement.
     *
     * @param xOffset The amount the mouse has moved in the X direction since the last frame.
     * @param yOffset The amount the mouse has moved in the Y direction since the last frame.
     */
    private fun processMouseInput(window: Long, xOffset: Float, yOffset: Float) {
        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_RIGHT) == GLFW_PRESS) {
            yaw += xOffset * mouseSensitivity
            pitch += yOffset * mouseSensitivity
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
        is Event.Frame -> processKeyboardInput(event.window, event.deltaTime)
        is Event.FramebufferResize -> calculateProjectionMatrix(event.width.toFloat(), event.height.toFloat())
        else -> {}
    }
}