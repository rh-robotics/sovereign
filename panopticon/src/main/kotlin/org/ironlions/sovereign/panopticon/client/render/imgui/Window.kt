package org.ironlions.sovereign.panopticon.client.render.imgui

import imgui.ImGui
import imgui.ImVec2
import imgui.flag.ImGuiFocusedFlags
import imgui.flag.ImGuiWindowFlags
import org.ironlions.sovereign.panopticon.client.render.Renderer
import org.ironlions.sovereign.panopticon.client.render.event.Event
import org.ironlions.sovereign.panopticon.client.render.event.EventDispatcher

abstract class Window(
    private val name: String, private val options: Int = ImGuiWindowFlags.None
) {
    protected var eventDispatcher = EventDispatcher()
    private var firstFrame: Boolean = true
    private var lastAvailableSpace: ImVec2? = null
    var availableSpace: ImVec2? = null
    var active: Boolean = false
    var hovering: Boolean = false

    fun frame(renderer: Renderer) {
        ImGui.begin(name, options)
        active = ImGui.isWindowFocused(ImGuiFocusedFlags.RootWindow)
        hovering =
            (ImGui.getMousePos().x in (ImGui.getWindowPosX())..(ImGui.getWindowPosX() + ImGui.getWindowWidth())) && (ImGui.getMousePos().y in (ImGui.getWindowPosY())..(ImGui.getWindowPosY() + ImGui.getWindowHeight()))

        availableSpace = ImGui.getContentRegionAvail()
        val availWidth = availableSpace!!.x.toInt()
        val availHeight = availableSpace!!.y.toInt()

        if (availableSpace != lastAvailableSpace) {
            eventDispatcher.broadcastToSubscribers(
                Event.FramebufferResize(
                    renderer.activeCamera.framebuffer, availWidth, availHeight
                )
            )
        }

        if (firstFrame) {
            firstFrame = false
            init(renderer)
        }

        content(renderer)
        ImGui.end()

        lastAvailableSpace = availableSpace
    }

    open fun init(renderer: Renderer) {}
    open fun content(renderer: Renderer) =
        ImGui.textColored(252, 163, 17, 255, "This window has no content.")
}