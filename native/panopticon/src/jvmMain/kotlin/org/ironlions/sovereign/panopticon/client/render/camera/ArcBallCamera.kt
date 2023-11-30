package org.ironlions.sovereign.panopticon.client.render.camera

import glm_.glm
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import org.ironlions.sovereign.panopticon.client.event.Event
import org.lwjgl.glfw.GLFW
import kotlin.math.cos
import kotlin.math.sin

class ArcBallCamera(
    framebufferWidth: Int,
    framebufferHeight: Int,
    position: Vec3 = Vec3(0, 0, 2),
    private var lookAt: Vec3 = Vec3(0),
    private var mouseSensitivity: Float = 0.01f,
    private var phi: Float = -glm.PIf / 4,
    private var theta: Float = -1.0f,
    private var radius: Float = 5.0f,
) : Camera(framebufferWidth, framebufferHeight, position) {
    init {
        calculateCameraVectors()
    }

    /** Recalculate all the camera vectors. */
    override fun calculateCameraVectors() {
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
        if (GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS) {
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
            glm.radians(45.0f), width / height, 0.1f, 100.0f
        )
    }

    override fun getViewMatrix(): Mat4 = glm.lookAt(position, lookAt, Vec3(0, 1,0))

    override fun onEvent(event: Event) = when (event) {
        is Event.Mouse -> processMouseInput(event.window, event.xOffset, event.yOffset)
        is Event.FramebufferResize -> onFramebufferResize(
            event.width.toFloat(), event.height.toFloat()
        )

        else -> {}
    }
}