package org.ironlions.panopticon.client.render.globjects

import imgui.ImGui
import org.lwjgl.opengl.GL11.GL_LINEAR
import org.lwjgl.opengl.GL11.GL_RGB
import org.lwjgl.opengl.GL11.GL_TEXTURE_2D
import org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER
import org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER
import org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE
import org.lwjgl.opengl.GL11.glBindTexture
import org.lwjgl.opengl.GL11.glTexImage2D
import org.lwjgl.opengl.GL11.glTexParameteri
import org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0
import org.lwjgl.opengl.GL30.glFramebufferTexture2D
import org.lwjgl.opengl.GL41.GL_FRAMEBUFFER
import org.lwjgl.opengl.GL41.GL_FRAMEBUFFER_COMPLETE
import org.lwjgl.opengl.GL41.glBindFramebuffer
import org.lwjgl.opengl.GL41.glCheckFramebufferStatus
import org.lwjgl.opengl.GL41.glGenFramebuffers
import org.lwjgl.opengl.GL41.glDeleteFramebuffers
import org.lwjgl.system.MemoryUtil

/**
 * A framebuffer. This isn't really flexible at all.
 *
 * @param width The width of the texture.
 * @param height The height of the texture.
 * TODO: Make this more flexible.
 */
class Framebuffer(
    val width: Int, val height: Int
) : GLObject {
    private val fbo = glGenFramebuffers()
    private val texture = Texture(GL_TEXTURE_2D)

    init {
        bind()
        texture.bind()

        glTexImage2D(
            GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, MemoryUtil.NULL
        )
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        glFramebufferTexture2D(
            GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture.texture, 0
        )

        //if (!complete()) throw RuntimeException("framebuffer is not complete.")

        unbind()
        texture.unbind()
    }

    /** Get the framebuffer status. */
    fun status() = glCheckFramebufferStatus(fbo)

    /** If the framebuffer is complete. */
    fun complete() = status() == GL_FRAMEBUFFER_COMPLETE

    /**
     * Resize the framebuffer.
     *
     * @param width The width of the texture.
     * @param height The height of the texture.
     */
    fun resize(width: Int, height: Int) {
        glBindTexture(GL_TEXTURE_2D, texture.texture)
        glTexImage2D(
            GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, MemoryUtil.NULL
        )
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        glFramebufferTexture2D(
            GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture.texture, 0
        )
    }

    fun imgui() {
        val pos = ImGui.getCursorScreenPos()
        val avail = ImGui.getContentRegionAvail()

        ImGui.getWindowDrawList()
            .addImage(fbo, pos.x, pos.y, pos.x + avail.x, pos.y + avail.y, 0f, 1f, 1f, 0f)
    }


    override fun bind() = glBindFramebuffer(GL_FRAMEBUFFER, fbo)
    override fun unbind() = glBindFramebuffer(GL_FRAMEBUFFER, 0)
    override fun close() = glDeleteFramebuffers(fbo)
}