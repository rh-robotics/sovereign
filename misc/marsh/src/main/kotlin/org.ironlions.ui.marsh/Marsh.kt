package org.ironlions.ui.marsh

import imgui.ImGui
import imgui.ImGuiWindowClass
import imgui.flag.ImGuiCond
import imgui.flag.ImGuiViewportFlags
import imgui.flag.ImGuiWindowFlags

object Marsh {
    private const val TOAST_PADDING_X = 10f
    private const val TOAST_PADDING_Y = 10f
    private const val TOAST_WINDOW_FLAGS: Int =
        ImGuiWindowFlags.NoTitleBar or ImGuiWindowFlags.NoScrollbar or ImGuiWindowFlags.NoMove or ImGuiWindowFlags.NoResize or ImGuiWindowFlags.NoInputs or ImGuiWindowFlags.NoBringToFrontOnFocus
    private val activeToasts: MutableList<Toast> = mutableListOf()

    fun show(toast: Toast) = activeToasts.add(toast)

    fun draw(deltaTime: Float) {
        var heightOffset = 0f

        activeToasts.forEach { it.timeToLive -= deltaTime }
        activeToasts.removeIf { it.timeToLive <= 0 }

        for (toast in activeToasts) {
            val toastWidth = ImGui.getIO().displaySizeX / 3
            val toastXPos = ImGui.getIO().displaySizeX - toastWidth - TOAST_PADDING_X
            val toastYPos = heightOffset + TOAST_PADDING_Y
            val windowClass = ImGuiWindowClass()

            windowClass.viewportFlagsOverrideSet = ImGuiViewportFlags.TopMost

            ImGui.setNextWindowClass(windowClass)
            ImGui.setNextWindowSize(toastWidth, 0f)
            ImGui.setNextWindowPos(toastXPos, toastYPos, ImGuiCond.Always)

            ImGui.begin(toast.hashCode().toString(), TOAST_WINDOW_FLAGS)
            ImGui.textColored(
                toast.classification.red,
                toast.classification.green,
                toast.classification.blue,
                255,
                "${toast.classification.classification} (${toast.timeToLive.toInt() + 1})"
            )
            ImGui.separator()
            ImGui.textWrapped(toast.formatted())
            heightOffset += ImGui.getWindowSizeY() + TOAST_PADDING_Y
            ImGui.end()
        }
    }
}