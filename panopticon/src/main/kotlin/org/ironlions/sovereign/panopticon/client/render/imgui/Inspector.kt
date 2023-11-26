package org.ironlions.sovereign.panopticon.client.render.imgui

import imgui.ImGui
import org.ironlions.sovereign.panopticon.client.render.Renderer

class Inspector : Window("Inspector") {
    override fun content(renderer: Renderer) {
        ImGui.textColored(252, 163, 17, 255, "This is the inspector.")
        ImGui.button("Inspect the")
        ImGui.checkbox("shit out", true)
        ImGui.bullet()
        ImGui.text("of everything.")
    }
}