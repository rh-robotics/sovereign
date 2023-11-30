package org.ironlions.panopticon.client.render.shader

import org.ironlions.panopticon.client.Logging
import org.lwjgl.opengl.GL41.GL_FRAGMENT_SHADER
import org.lwjgl.opengl.GL41.GL_INFO_LOG_LENGTH
import org.lwjgl.opengl.GL41.GL_LINK_STATUS
import org.lwjgl.opengl.GL41.GL_TRUE
import org.lwjgl.opengl.GL41.GL_VERTEX_SHADER
import org.lwjgl.opengl.GL41.glAttachShader
import org.lwjgl.opengl.GL41.glCreateProgram
import org.lwjgl.opengl.GL41.glGetUniformLocation
import org.lwjgl.opengl.GL41.glGetProgrami
import org.lwjgl.opengl.GL41.glLinkProgram
import org.lwjgl.opengl.GL41.glUseProgram
import org.lwjgl.opengl.GL41.glDeleteProgram
import org.lwjgl.opengl.GL41.glGetShaderInfoLog
import java.io.Closeable
import java.nio.ByteBuffer

/**
 * An OpenGL shader program.
 *
 * @param name The name of the program.
 * @param vertexSource The source code of the vertex shader.
 * @param fragmentSource The source code of the fragment shader.
 */
class Program(private val name: String, vertexSource: ByteBuffer, fragmentSource: ByteBuffer) :
    Closeable {
    private val program = glCreateProgram()

    init {
        Shader(GL_VERTEX_SHADER, "vertex", vertexSource).use { vertex ->
            Shader(GL_FRAGMENT_SHADER, "fragment", fragmentSource).use { fragment ->
                attach(vertex)
                attach(fragment)
                link()
            }
        }
    }

    /**
     * Attach a shader to a program.
     *
     * @param shader The shader to attach.
     */
    fun attach(shader: Shader) = glAttachShader(program, shader.shader)

    /** Link the attached shaders together into a program. */
    fun link() {
        glLinkProgram(program)
        printProgramInfoLog()

        if (glGetProgrami(program, GL_LINK_STATUS) != GL_TRUE) {
            throw IllegalStateException("Linking of program '$name' failed.")
        } else {
            Logging.logger.debug { "Linking of program '$name' succeeded." }
        }
    }

    /** Bind the program. */
    fun use() = glUseProgram(program)

    /**
     * Get the location of a uniform in the program.
     *
     * @param name The uniform name.
     */
    fun loc(name: String) = glGetUniformLocation(program, name)

    /** Print what's gone wrong! */
    private fun printProgramInfoLog() {
        val infoLogLength = glGetProgrami(program, GL_INFO_LOG_LENGTH)

        if (infoLogLength > 0) {
            Logging.logger.error { "Program compilation failed!" }
            glGetShaderInfoLog(program).lines().forEach {
                Logging.logger.error { it }
            }
        }
    }

    override fun close() = glDeleteProgram(program)
}