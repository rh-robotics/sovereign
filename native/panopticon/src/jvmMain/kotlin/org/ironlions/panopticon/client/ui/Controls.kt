package org.ironlions.panopticon.client.ui

import glm_.glm
import imgui.ImGui
import imgui.flag.ImGuiDir
import org.ironlions.panopticon.client.ClientApplication
import org.ironlions.panopticon.client.render.Renderer
import org.ironlions.ui.marsh.Marsh
import org.ironlions.ui.marsh.Toast

class Controls : Window("Controls", waitForDataSource = true) {
    private val playbackSpeed: IntArray = intArrayOf(1)
    private var currentPacketIndex: Int = 0
    private var isPlaying: Boolean = false

    override fun content(renderer: Renderer) {
        val inspector = ClientApplication.windows[Inspector::class] as Inspector
        val numPackets = inspector.dataTransceiver!!.size()

        ImGui.text("Fine Timing")
        if (ImGui.arrowButton("Backward Tick", ImGuiDir.Left)) {
            isPlaying = false
            currentPacketIndex -= 1
            inspector.dataTransceiver!!.prior()
        }

        ImGui.sameLine()
        if (ImGui.button(if (isPlaying) "Pause" else "Play")) isPlaying = !isPlaying

        ImGui.sameLine()
        if (ImGui.arrowButton("Forward Tick", ImGuiDir.Right)) {
            isPlaying = false
            currentPacketIndex += 1
            inspector.dataTransceiver!!.next()
        }

        currentPacketIndex = glm.clamp(currentPacketIndex, 0, numPackets - 1)

        ImGui.sameLine()
        ImGui.progressBar(
            (1f / (numPackets - 1)) * (currentPacketIndex),
            0f,
            0f,
            "Data Packet ${currentPacketIndex + 1}/$numPackets"
        )

        ImGui.separator()

        ImGui.text("Playback Settings")
        ImGui.sliderInt("Playback Speed", playbackSpeed, 1, 8, "%d")

        if (isPlaying) {
            Marsh.show(Toast.Error("Automatic playback is not currently supported."))
            isPlaying = false
        }
    }
}