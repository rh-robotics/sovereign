package org.ironlions.panopticon.client.ui

import imgui.ImGui
import imgui.flag.ImGuiCol


/**
 * Install our ImGui theme so it looks nice.
 *
 * https://github.com/GraphicsProgramming/dear-imgui-styles?tab=readme-ov-file#dougblinks
 */
fun installImGuiTheme() {
    val style = ImGui.getStyle()

    style.setColor(ImGuiCol.Text, 1f, 1f, 1f, 1f)
    style.setColor(ImGuiCol.TextDisabled, 0.50f, 0.50f, 0.50f, 1f)
    style.setColor(ImGuiCol.WindowBg, 0.10f, 0.10f, 0.10f, 1f)
    style.setColor(ImGuiCol.ChildBg, 0f, 0f, 0f, 0f)
    style.setColor(ImGuiCol.PopupBg, 0.19f, 0.19f, 0.19f, 0.92f)
    style.setColor(ImGuiCol.Border, 0.19f, 0.19f, 0.19f, 0.29f)
    style.setColor(ImGuiCol.BorderShadow, 0f, 0f, 0f, 0.24f)
    style.setColor(ImGuiCol.FrameBg, 0.05f, 0.05f, 0.05f, 0.54f)
    style.setColor(ImGuiCol.FrameBgHovered, 0.19f, 0.19f, 0.19f, 0.54f)
    style.setColor(ImGuiCol.FrameBgActive, 0.20f, 0.22f, 0.23f, 1f)
    style.setColor(ImGuiCol.TitleBg, 0f, 0f, 0f, 1f)
    style.setColor(ImGuiCol.TitleBgActive, 0.06f, 0.06f, 0.06f, 1f)
    style.setColor(ImGuiCol.TitleBgCollapsed, 0f, 0f, 0f, 1f)
    style.setColor(ImGuiCol.MenuBarBg, 0.14f, 0.14f, 0.14f, 1f)
    style.setColor(ImGuiCol.ScrollbarBg, 0.05f, 0.05f, 0.05f, 0.54f)
    style.setColor(ImGuiCol.ScrollbarGrab, 0.34f, 0.34f, 0.34f, 0.54f)
    style.setColor(ImGuiCol.ScrollbarGrabHovered, 0.40f, 0.40f, 0.40f, 0.54f)
    style.setColor(ImGuiCol.ScrollbarGrabActive, 0.56f, 0.56f, 0.56f, 0.54f)
    style.setColor(ImGuiCol.CheckMark, 0.33f, 0.67f, 0.86f, 1f)
    style.setColor(ImGuiCol.SliderGrab, 0.34f, 0.34f, 0.34f, 0.54f)
    style.setColor(ImGuiCol.SliderGrabActive, 0.56f, 0.56f, 0.56f, 0.54f)
    style.setColor(ImGuiCol.Button, 0.05f, 0.05f, 0.05f, 0.54f)
    style.setColor(ImGuiCol.ButtonHovered, 0.19f, 0.19f, 0.19f, 0.54f)
    style.setColor(ImGuiCol.ButtonActive, 0.20f, 0.22f, 0.23f, 1f)
    style.setColor(ImGuiCol.Header, 0f, 0f, 0f, 0.52f)
    style.setColor(ImGuiCol.HeaderHovered, 0f, 0f, 0f, 0.36f)
    style.setColor(ImGuiCol.HeaderActive, 0.20f, 0.22f, 0.23f, 0.33f)
    style.setColor(ImGuiCol.Separator, 0.28f, 0.28f, 0.28f, 0.29f)
    style.setColor(ImGuiCol.SeparatorHovered, 0.44f, 0.44f, 0.44f, 0.29f)
    style.setColor(ImGuiCol.SeparatorActive, 0.40f, 0.44f, 0.47f, 1f)
    style.setColor(ImGuiCol.ResizeGrip, 0.28f, 0.28f, 0.28f, 0.29f)
    style.setColor(ImGuiCol.ResizeGripHovered, 0.44f, 0.44f, 0.44f, 0.29f)
    style.setColor(ImGuiCol.ResizeGripActive, 0.40f, 0.44f, 0.47f, 1f)
    style.setColor(ImGuiCol.Tab, 0f, 0f, 0f, 0.52f)
    style.setColor(ImGuiCol.TabHovered, 0.14f, 0.14f, 0.14f, 1f)
    style.setColor(ImGuiCol.TabActive, 0.20f, 0.20f, 0.20f, 0.36f)
    style.setColor(ImGuiCol.TabUnfocused, 0f, 0f, 0f, 0.52f)
    style.setColor(ImGuiCol.TabUnfocusedActive, 0.14f, 0.14f, 0.14f, 1f)
    style.setColor(ImGuiCol.DockingPreview, 0.33f, 0.67f, 0.86f, 1f)
    style.setColor(ImGuiCol.DockingEmptyBg, 0.10f, 0.10f, 0.10f, 1f)
    style.setColor(ImGuiCol.PlotLines, 1f, 0f, 0f, 1f)
    style.setColor(ImGuiCol.PlotLinesHovered, 1f, 0f, 0f, 1f)
    style.setColor(ImGuiCol.PlotHistogram, 1f, 0f, 0f, 1f)
    style.setColor(ImGuiCol.PlotHistogramHovered, 1f, 0f, 0f, 1f)
    style.setColor(ImGuiCol.TableHeaderBg, 0f, 0f, 0f, 0.52f)
    style.setColor(ImGuiCol.TableBorderStrong, 0f, 0f, 0f, 0.52f)
    style.setColor(ImGuiCol.TableBorderLight, 0.28f, 0.28f, 0.28f, 0.29f)
    style.setColor(ImGuiCol.TableRowBg, 0f, 0f, 0f, 0f)
    style.setColor(ImGuiCol.TableRowBgAlt, 1f, 1f, 1f, 0.06f)
    style.setColor(ImGuiCol.TextSelectedBg, 0.20f, 0.22f, 0.23f, 1f)
    style.setColor(ImGuiCol.DragDropTarget, 0.33f, 0.67f, 0.86f, 1f)
    style.setColor(ImGuiCol.NavHighlight, 1f, 0f, 0f, 1f)
    style.setColor(ImGuiCol.NavWindowingHighlight, 1f, 0f, 0f, 0.70f)
    style.setColor(ImGuiCol.NavWindowingDimBg, 1f, 0f, 0f, 0.20f)
    style.setColor(ImGuiCol.ModalWindowDimBg, 0.10f, 0.10f, 0.10f, 0.8f)

    style.windowPadding.set(8f, 8f)
    style.framePadding.set(5f, 2f)
    style.cellPadding.set(6f, 6f)
    style.itemSpacing.set(6f, 6f)
    style.itemInnerSpacing.set(6f, 6f)
    style.touchExtraPadding.set(0f, 0f)
    style.indentSpacing = 25f
    style.scrollbarSize = 15f
    style.grabMinSize = 10f
    style.windowBorderSize = 2f
    style.childBorderSize = 1f
    style.popupBorderSize = 1f
    style.frameBorderSize = 1f
    style.tabBorderSize = 1f
    style.windowRounding = 7f
    style.childRounding = 4f
    style.frameRounding = 3f
    style.popupRounding = 4f
    style.scrollbarRounding = 9f
    style.grabRounding = 3f
    style.logSliderDeadzone = 4f
    style.tabRounding = 4f
}