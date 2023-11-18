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

class Camera(
    private val position: Vec3 = Vec3(0, 0, 1),
    private val cameraSpeed: Float = 0.05f,
) {
    var projectionMatrix: Mat4 = glm.perspective(glm.radians(45.0f), 1.7142857f, 0.1f, 100.0f)
    private var target: Vec3 = Vec3(0)
    private var direction = (position - target).normalize()
    private val right = vertical.cross(direction).normalize()
    private val up = direction.cross(right)

    fun getViewMatrix(): Mat4 = glm.lookAt(position, position + front, up)

    fun processInput(window: Long, deltaTime: Float) {
        if (GLFW_PRESS == glfwGetKey(
                window, GLFW_KEY_W
            )
        ) position += front * cameraSpeed * deltaTime

        if (GLFW_PRESS == glfwGetKey(
                window,
                GLFW_KEY_S
            )
        ) position -= front * cameraSpeed * deltaTime

        if (GLFW_PRESS == glfwGetKey(window, GLFW_KEY_D)) position += front.cross(up)
            .normalize() * cameraSpeed * deltaTime

        if (GLFW_PRESS == glfwGetKey(window, GLFW_KEY_A)) position -= front.cross(up)
            .normalize() * cameraSpeed * deltaTime
    }

    fun updateProjectionMatrix(width: Int, height: Int) {
        projectionMatrix =
            glm.perspective(glm.radians(45.0f), width.toFloat() / height, 0.1f, 100.0f)
    }

    companion object {
        private val front = Vec3(0, 0, -1)
        private val vertical = Vec3(0, 1, 0)
    }
}