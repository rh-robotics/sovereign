package org.ironlions.sovereign.panopticon.client.shader

import org.ironlions.sovereign.panopticon.client.Logging
import org.lwjgl.opengl.GL20.GL_COMPILE_STATUS
import org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH
import org.lwjgl.opengl.GL20.GL_TRUE
import org.lwjgl.opengl.GL20.glCompileShader
import org.lwjgl.opengl.GL20.glCreateShader
import org.lwjgl.opengl.GL20.glGetShaderInfoLog
import org.lwjgl.opengl.GL20.glGetShaderi
import org.lwjgl.opengl.GL20.glShaderSource
import org.lwjgl.system.MemoryStack
import java.nio.ByteBuffer

/**
 * An OpenGL shader.
 *
 * @param type The type of shader.
 * @param name The name of the shader.
 * @param source The shader source code.
 */
class Shader(type: Int, name: String, source: ByteBuffer) {
    var shader: Int
        private set

    init {
        MemoryStack.stackPush().use { stack ->
            shader = glCreateShader(type)

            glShaderSource(
                shader,
                stack.pointers(source),
                stack.ints(source.remaining())
            )

            glCompileShader(shader)
            printShaderInfoLog()

            if (glGetShaderi(shader, GL_COMPILE_STATUS) != GL_TRUE) {
                throw IllegalStateException("Compilation of shader '$name' failed");
            } else {
                Logging.logger.debug { "Compilation of shader '$name' succeeded." }
            }
        }
    }

    /** Print what's gone wrong! */
    private fun printShaderInfoLog() {
        val infologLength = glGetShaderi(shader, GL_INFO_LOG_LENGTH)

        if (infologLength > 0) {
            Logging.logger.error { "Shader linking failed!" }
            glGetShaderInfoLog(shader).lines().forEach {
                Logging.logger.error { it }
            }
        }
    }
}