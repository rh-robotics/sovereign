package org.ironlions.sovereign.panopticon.client.render.imgui

import imgui.ImGui
import imgui.flag.ImGuiCol

/**
 * Install our ImGui theme so it looks nice.
 *
 * https://github.com/GraphicsProgramming/dear-imgui-styles?tab=readme-ov-file#dougblinks
 */
fun installImGuiTheme() {
    ImGui.styleColorsDark()
    val style = ImGui.getStyle()

    style.windowMinSize.set(160f, 20f)
    style.framePadding.set(4f, 2f)
    style.itemSpacing.set(6f, 2f)
    style.itemInnerSpacing.set(6f, 4f)
    style.alpha = 0.95f
    style.windowRounding = 4.0f
    style.frameRounding = 2.0f
    style.indentSpacing = 6.0f
    style.itemInnerSpacing.set(2f, 4f)
    style.columnsMinSpacing = 50.0f
    style.grabMinSize = 14.0f
    style.grabRounding = 16.0f
    style.scrollbarSize = 12.0f
    style.scrollbarRounding = 16.0f

    style.setColor(ImGuiCol.Text, 0.86f, 0.93f, 0.89f, 0.78f);
    style.setColor(ImGuiCol.TextDisabled, 0.86f, 0.93f, 0.89f, 0.28f);
    style.setColor(ImGuiCol.WindowBg, 0.13f, 0.14f, 0.17f, 1.00f);
    style.setColor(ImGuiCol.Border, 0.31f, 0.31f, 1.00f, 0.00f);
    style.setColor(ImGuiCol.BorderShadow, 0.00f, 0.00f, 0.00f, 0.00f);
    style.setColor(ImGuiCol.FrameBg, 0.20f, 0.22f, 0.27f, 1.00f);
    style.setColor(ImGuiCol.FrameBgHovered, 0.92f, 0.18f, 0.29f, 0.78f);
    style.setColor(ImGuiCol.FrameBgActive, 0.92f, 0.18f, 0.29f, 1.00f);
    style.setColor(ImGuiCol.TitleBg, 0.20f, 0.22f, 0.27f, 1.00f);
    style.setColor(ImGuiCol.TitleBgCollapsed, 0.20f, 0.22f, 0.27f, 0.75f);
    style.setColor(ImGuiCol.TitleBgActive, 0.92f, 0.18f, 0.29f, 1.00f);
    style.setColor(ImGuiCol.MenuBarBg, 0.20f, 0.22f, 0.27f, 0.47f);
    style.setColor(ImGuiCol.ScrollbarBg, 0.20f, 0.22f, 0.27f, 1.00f);
    style.setColor(ImGuiCol.ScrollbarGrab, 0.09f, 0.15f, 0.16f, 1.00f);
    style.setColor(ImGuiCol.ScrollbarGrabHovered, 0.92f, 0.18f, 0.29f, 0.78f);
    style.setColor(ImGuiCol.ScrollbarGrabActive, 0.92f, 0.18f, 0.29f, 1.00f);
    style.setColor(ImGuiCol.CheckMark, 0.71f, 0.22f, 0.27f, 1.00f);
    style.setColor(ImGuiCol.SliderGrab, 0.47f, 0.77f, 0.83f, 0.14f);
    style.setColor(ImGuiCol.SliderGrabActive, 0.92f, 0.18f, 0.29f, 1.00f);
    style.setColor(ImGuiCol.Button, 0.47f, 0.77f, 0.83f, 0.14f);
    style.setColor(ImGuiCol.ButtonHovered, 0.92f, 0.18f, 0.29f, 0.86f);
    style.setColor(ImGuiCol.ButtonActive, 0.92f, 0.18f, 0.29f, 1.00f);
    style.setColor(ImGuiCol.Header, 0.92f, 0.18f, 0.29f, 0.76f);
    style.setColor(ImGuiCol.HeaderHovered, 0.92f, 0.18f, 0.29f, 0.86f);
    style.setColor(ImGuiCol.HeaderActive, 0.92f, 0.18f, 0.29f, 1.00f);
    style.setColor(ImGuiCol.Separator, 0.14f, 0.16f, 0.19f, 1.00f);
    style.setColor(ImGuiCol.SeparatorHovered, 0.92f, 0.18f, 0.29f, 0.78f);
    style.setColor(ImGuiCol.SeparatorActive, 0.92f, 0.18f, 0.29f, 1.00f);
    style.setColor(ImGuiCol.ResizeGrip, 0.47f, 0.77f, 0.83f, 0.04f);
    style.setColor(ImGuiCol.ResizeGripHovered, 0.92f, 0.18f, 0.29f, 0.78f);
    style.setColor(ImGuiCol.ResizeGripActive, 0.92f, 0.18f, 0.29f, 1.00f);
    style.setColor(ImGuiCol.PlotLines, 0.86f, 0.93f, 0.89f, 0.63f);
    style.setColor(ImGuiCol.PlotLinesHovered, 0.92f, 0.18f, 0.29f, 1.00f);
    style.setColor(ImGuiCol.PlotHistogram, 0.86f, 0.93f, 0.89f, 0.63f);
    style.setColor(ImGuiCol.PlotHistogramHovered, 0.92f, 0.18f, 0.29f, 1.00f);
    style.setColor(ImGuiCol.TextSelectedBg, 0.92f, 0.18f, 0.29f, 0.43f);
    style.setColor(ImGuiCol.PopupBg, 0.20f, 0.22f, 0.27f, 0.9f);
}