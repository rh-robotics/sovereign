package org.ironlions.sovereign.panopticon.client.ui

import imgui.ImGui
import imgui.extension.imguifiledialog.ImGuiFileDialog
import imgui.extension.imguifiledialog.flag.ImGuiFileDialogFlags
import imgui.flag.ImGuiStyleVar
import imgui.flag.ImGuiWindowFlags
import imgui.internal.flag.ImGuiItemFlags

open class FilePickerType(val count: Int, val title: String) {
    class OneFile() : FilePickerType(1, "Choose One File")
    class SomeFiles(count: Int) : FilePickerType(count, "Choose $count Files")
    class ManyFiles() : FilePickerType(0, "Choose Many Files")
}

private const val MODAL_WINDOW_FLAGS: Int =
    ImGuiWindowFlags.NoCollapse or ImGuiWindowFlags.NoResize or ImGuiWindowFlags.NoMove
private const val DIALOG_WINDOW_FLAGS =
    ImGuiWindowFlags.NoCollapse

fun modal(name: String, content: (() -> Unit)) {
    if (ImGui.beginPopupModal(
            name, MODAL_WINDOW_FLAGS
        )
    ) {
        content()
        ImGui.endPopup()
    }

    ImGui.openPopup(name)
}

fun window(name: String, content: (() -> Unit)) {
    if (ImGui.begin(name, DIALOG_WINDOW_FLAGS)) {
        content()

        ImGui.end()
    }
}

fun filePicker(
    name: String,
    type: FilePickerType,
    filter: String? = null,
    content: ((Map<String, String>) -> Unit)
) {
    ImGuiFileDialog.openDialog(
        name,
        type.title,
        filter,
        System.getProperty("user.home"),
        "",
        type.count,
        7,
        ImGuiFileDialogFlags.DontShowHiddenFiles
    )

    if (ImGuiFileDialog.display(
            name,
            DIALOG_WINDOW_FLAGS or ImGuiWindowFlags.NoScrollWithMouse,
            600f,
            400f,
            10000f,
            10000f
        )
    ) {
        if (ImGuiFileDialog.isOk()) {
            content(ImGuiFileDialog.getSelection())
        }
    }
}

fun button(string: String, action: (() -> Unit) = { TODO() }) {
    if (ImGui.button(string)) action()
}

fun disabled(content: (() -> Unit)) {
    imgui.internal.ImGui.pushItemFlag(ImGuiItemFlags.Disabled, true)
    ImGui.pushStyleVar(ImGuiStyleVar.Alpha, ImGui.getStyle().alpha * 0.5f)
    content()
    ImGui.popStyleVar()
    imgui.internal.ImGui.popItemFlag()
}

fun errorText(string: String) = ImGui.textColored(255, 0, 0, 255, string)
fun warningText(string: String) = ImGui.textColored(252, 163, 17, 255, string)
