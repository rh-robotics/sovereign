package org.ironlions.sovereign.panopticon.client.render

import glm_.glm
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import org.lwjgl.glfw.GLFW.GLFW_KEY_A
import org.lwjgl.glfw.GLFW.GLFW_KEY_D
import org.lwjgl.glfw.GLFW.GLFW_KEY_S
import org.lwjgl.glfw.GLFW.GLFW_KEY_W
import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.glfwGetKey
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

class Camera(
    private val position: Vec3 = Vec3(0, 0, 2),
    private var up: Vec3 = Vec3(0.0f, 1.0f, 0.0f),
    private var yaw: Float = -90.0f,
    private var pitch: Float = 0f,
    private val cameraSpeed: Float = 0.05f,
    private val mouseSensitivity: Float = 0.1f,
) {
    var projectionMatrix: Mat4 = glm.perspective(glm.radians(45.0f), 1.7142857f, 0.1f, 100.0f)
    private var front: Vec3 = Vec3(0.0f, 0.0f, -1.0f)
    private var worldUp: Vec3 = Vec3(up.x, up.y, up.z)
    private lateinit var right: Vec3

    init {
        calculateCameraVectors()
    }

    fun getViewMatrix(renderer: Renderer): Mat4 {
        projectionMatrix =
            glm.perspective(
                glm.radians(45.0f),
                renderer.windowWidth.toFloat() / renderer.windowHeight.toFloat(),
                0.1f,
                100.0f
            )

        return glm.lookAt(position, position + front, up)
    }

    private fun calculateCameraVectors() {
        front.x = cos(glm.radians(yaw)) * cos(glm.radians(pitch))
        front.y = sin(glm.radians(pitch))
        front.z = sin(glm.radians(yaw)) * cos(glm.radians(pitch))
        front = glm.normalize(front)
        right = glm.normalize(glm.cross(front, worldUp))
        up    = glm.normalize(glm.cross(right, front))
    }

    fun processKeyboardInput(window: Long, deltaTime: Float) {
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
    }

    fun processMouseInput(xOffset: Float, yOffset: Float) {
        yaw += xOffset * mouseSensitivity
        pitch += yOffset * mouseSensitivity
        pitch = max(min(pitch, 89.0f), -89.0f)
        calculateCameraVectors()
    }
}