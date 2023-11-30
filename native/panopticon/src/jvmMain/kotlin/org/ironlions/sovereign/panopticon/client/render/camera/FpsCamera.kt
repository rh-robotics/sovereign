package org.ironlions.sovereign.panopticon.client.render.camera

import glm_.glm
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import org.ironlions.sovereign.panopticon.client.event.Event
import org.lwjgl.glfw.GLFW.GLFW_KEY_A
import org.lwjgl.glfw.GLFW.GLFW_KEY_C
import org.lwjgl.glfw.GLFW.GLFW_KEY_D
import org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL
import org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT
import org.lwjgl.glfw.GLFW.GLFW_KEY_S
import org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE
import org.lwjgl.glfw.GLFW.GLFW_KEY_W
import org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT
import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.glfwGetKey
import org.lwjgl.glfw.GLFW.glfwGetMouseButton
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

class FpsCamera(
    framebufferWidth: Int,
    framebufferHeight: Int,
    position: Vec3 = Vec3(-35, 20, 0),
    private var mouseSensitivity: Float = 0.35f,
    private var yaw: Float = 0f,
    private var pitch: Float = -35.0f,
    private val cameraSpeed: Float = 3f,
) : Camera(framebufferWidth, framebufferHeight, position) {
    private var up: Vec3 = Vec3(0.0f, 1.0f, 0.0f)
    private var worldUp: Vec3 = Vec3(up.x, up.y, up.z)
    private var front: Vec3 = Vec3(0.0f, 0.0f, -1.0f)
    private lateinit var right: Vec3

    init {
        calculateCameraVectors()
    }

    /** Recalculate all the camera vectors. */
    override fun calculateCameraVectors() {
        front.x = cos(glm.radians(yaw)) * cos(glm.radians(pitch))
        front.y = sin(glm.radians(pitch))
        front.z = sin(glm.radians(yaw)) * cos(glm.radians(pitch))
        front = glm.normalize(front)
        right = glm.normalize(glm.cross(front, worldUp))
        up = glm.normalize(glm.cross(right, front))
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

            pitch = min(max(pitch, -89.9f), 89.9f)

            calculateCameraVectors()
        }
    }

    /** Process some keyword input.
     *
     * @param window The window receiving the event.
     * @param deltaTime The time since the last frame.
     */
    private fun processKeyboardInput(window: Long, deltaTime: Float) {
        var velocity = cameraSpeed * deltaTime

        if (GLFW_PRESS == glfwGetKey(window, GLFW_KEY_LEFT_CONTROL)) velocity /= 3
        if (GLFW_PRESS == glfwGetKey(window, GLFW_KEY_LEFT_SHIFT)) velocity *= 3
        if (GLFW_PRESS == glfwGetKey(window, GLFW_KEY_W)) position = position + (front * velocity)
        if (GLFW_PRESS == glfwGetKey(window, GLFW_KEY_S)) position = position - (front * velocity)
        if (GLFW_PRESS == glfwGetKey(window, GLFW_KEY_D)) position = position + (right * velocity)
        if (GLFW_PRESS == glfwGetKey(window, GLFW_KEY_A)) position = position - (right * velocity)
        if (GLFW_PRESS == glfwGetKey(window, GLFW_KEY_SPACE)) position = position + (worldUp * velocity)
        if (GLFW_PRESS == glfwGetKey(window, GLFW_KEY_C)) position = position - (worldUp * velocity)

        println("$position")
    }

    /** Calculate the projection matrix.
     *
     * @param width The width of the framebuffer.
     * @param height The height of the framebuffer.
     */
    private fun calculateProjectionMatrix(width: Float, height: Float) {
        projectionMatrix = glm.perspective(
            glm.radians(45.0f), width / height, 0.1f, 100.0f
        )
    }

    override fun getViewMatrix(): Mat4 = glm.lookAt(position, front + position, up)

    override fun onEvent(event: Event) = when (event) {
        is Event.Mouse -> processMouseInput(event.window, event.xOffset, event.yOffset)
        is Event.Frame -> processKeyboardInput(event.window, event.deltaTime)
        is Event.FramebufferResize -> calculateProjectionMatrix(
            event.width.toFloat(), event.height.toFloat()
        )

        else -> {}
    }
}