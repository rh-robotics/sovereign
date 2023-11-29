package org.ironlions.sovereign.panopticon.client.render.globjects

import org.lwjgl.opengl.GL41.glBindTexture
import org.lwjgl.opengl.GL41.glDeleteTextures
import org.lwjgl.opengl.GL41.glGenTextures

/**
 * A texture inside a framebuffer.
 *
 * @param type The type of texture.
 */
class Texture(private val type: Int) : GLObject {
    internal val texture = glGenTextures()

    override fun bind() = glBindTexture(type, texture)
    override fun unbind() = glBindTexture(type, 0)
    override fun close() = glDeleteTextures(texture)
}