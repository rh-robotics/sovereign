package org.ironlions.sovereign.panopticon.client.shader

import org.ironlions.sovereign.panopticon.client.Logging
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER
import org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH
import org.lwjgl.opengl.GL20.GL_LINK_STATUS
import org.lwjgl.opengl.GL20.GL_TRUE
import org.lwjgl.opengl.GL20.GL_VERTEX_SHADER
import org.lwjgl.opengl.GL20.glAttachShader
import org.lwjgl.opengl.GL20.glCreateProgram
import org.lwjgl.opengl.GL20.glGetProgrami
import org.lwjgl.opengl.GL20.glLinkProgram
import org.lwjgl.opengl.GL20.glUseProgram
import java.nio.ByteBuffer

class Program(private val name: String, vertexSource: ByteBuffer, fragmentSource: ByteBuffer) {
    private val program = glCreateProgram()

    init {
        attach(Shader(GL_VERTEX_SHADER, "vertex", vertexSource))
        attach(Shader(GL_FRAGMENT_SHADER, "fragment", fragmentSource))
        link()
    }

    fun attach(shader: Shader) = glAttachShader(program, shader.shader)

    fun link() {
        glLinkProgram(program)
        printProgramInfoLog()

        if (glGetProgrami(program, GL_LINK_STATUS) != GL_TRUE) {
            throw IllegalStateException("Linking of program '$name' failed.")
        } else {
            Logging.logger.debug { "Linking of program '$name' succeeded." }
        }
    }

    fun use() = glUseProgram(program)

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