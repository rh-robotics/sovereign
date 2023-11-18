package org.ironlions.sovereign.panopticon.client.render.shader

import org.ironlions.sovereign.panopticon.client.Logging
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER
import org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH
import org.lwjgl.opengl.GL20.GL_LINK_STATUS
import org.lwjgl.opengl.GL20.GL_TRUE
import org.lwjgl.opengl.GL20.GL_VERTEX_SHADER
import org.lwjgl.opengl.GL20.glAttachShader
import org.lwjgl.opengl.GL20.glCreateProgram
import org.lwjgl.opengl.GL20.glGetUniformLocation
import org.lwjgl.opengl.GL20.glGetProgrami
import org.lwjgl.opengl.GL20.glLinkProgram
import org.lwjgl.opengl.GL20.glUseProgram
import java.nio.ByteBuffer

/**
 * An OpenGL shader program.
 *
 * @param name The name of the program.
 * @param vertexSource The source code of the vertex shader.
 * @param fragmentSource The source code of the fragment shader.
 */
class Program(private val name: String, vertexSource: ByteBuffer, fragmentSource: ByteBuffer) {
    private val program = glCreateProgram()

    init {
        val vertex = Shader(GL_VERTEX_SHADER, "vertex", vertexSource)
        val fragment = Shader(GL_FRAGMENT_SHADER, "fragment", fragmentSource)

        attach(vertex)
        attach(fragment)
        link()

        vertex.delete()
        fragment.delete()
    }

    /** Attach a shader to a program. */
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

    /** Get the location of a uniform in the program. */
    fun loc(name: String) = glGetUniformLocation(program, name)

    /** Print what's gone wrong! */
    private fun printProgramInfoLog() {
        val infoLogLength = glGetProgrami(program, GL_INFO_LOG_LENGTH)

        if (infoLogLength > 0) {
            Logging.logger.error { "Program compilation failed!" }
            GL20.glGetShaderInfoLog(program).lines().forEach {
                Logging.logger.error { it }
            }
        }
    }
}