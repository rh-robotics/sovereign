package org.ironlions.sovereign.panopticon.client.render.imgui

import com.google.protobuf.InvalidProtocolBufferException
import imgui.ImGui
import imgui.flag.ImGuiSelectableFlags
import imgui.flag.ImGuiTableColumnFlags
import imgui.flag.ImGuiTableFlags
import imgui.type.ImBoolean
import org.ironlions.sovereign.panopticon.client.data.DataSource
import org.ironlions.sovereign.panopticon.client.data.RecordDataSource
import org.ironlions.sovereign.panopticon.client.render.Renderer
import org.ironlions.sovereign.panopticon.client.util.titlecase
import org.ironlions.sovereign.proto.environment.Environment
import org.ironlions.ui.marsh.Marsh
import org.ironlions.ui.marsh.Toast
import kotlin.io.path.Path

private enum class DataSourcePickingStage {
    START, BLUETOOTH, RECORD
}

private open class InherentProperty(val human: String) {
    class Uuid : InherentProperty("UUID")
    class Region : InherentProperty("Region")
    class Model : InherentProperty("Model")
}

class Inspector : Window("Inspector") {
    private var dataSourcePickingStage = DataSourcePickingStage.START
    private var displayableInherentProperty: Map<InherentProperty, ImBoolean> = mapOf(
        InherentProperty.Uuid() to ImBoolean(false),
        InherentProperty.Region() to ImBoolean(true),
        InherentProperty.Model() to ImBoolean(false),
    )
    var dataSource: DataSource? = null
    var wantConnect: Boolean = false

    override fun content(renderer: Renderer) {
        // Easier development.
        if (dataSource == null && System.getenv("PANOPTICON_PANDAT") != null) dataSource =
            RecordDataSource(Path(System.getenv("PANOPTICON_PANDAT")))

        if (wantConnect) pickDataSource()
        if (dataSource == null) {
            warningText("No data source!")
            return
        } else wantConnect = false

        ImGui.setNextItemOpen(true)
        if (ImGui.treeNode("Things")) {
            for (thing in dataSource!!.things()) {
                if (ImGui.treeNodeEx(thing.humanName)) {
                    displayThingInfo(thing)
                    ImGui.treePop()
                }
            }

            ImGui.treePop()
        }

        ImGui.setNextItemOpen(true)
        if (ImGui.treeNode("Filter")) {
            if (ImGui.beginTable(
                    "Filter",
                    1,
                    ImGuiTableFlags.Resizable or ImGuiTableFlags.NoSavedSettings or ImGuiTableFlags.Borders
                )
            ) {
                for (extra in displayableInherentProperty) {
                    ImGui.tableNextRow()
                    ImGui.tableNextColumn()
                    ImGui.selectable(
                        extra.key.human, extra.value, ImGuiSelectableFlags.SpanAllColumns
                    )
                }

                ImGui.endTable()
            }

            ImGui.treePop()
        }
    }

    private fun displayThingInfo(thing: Environment.Thing) {
        val extra = displayableInherentProperty.filter { it.value.get() }.keys.associate {
            it.human to when (it) {
                is InherentProperty.Uuid -> thing.uuid.toString()
                is InherentProperty.Region -> "(${thing.geometry.region.x1}, ${thing.geometry.region.y1}, ${thing.geometry.region.z1}) - (${thing.geometry.region.x2}, ${thing.geometry.region.y2}, ${thing.geometry.region.z2})"
                is InherentProperty.Model -> thing.geometry.model.toString()
                else -> throw RuntimeException("Unreachable.")
            }
        }

        displayDataTable(thing.data, extra)

        if (thing.data.childrenList.isNotEmpty() && ImGui.treeNodeEx("Children")) {
            thing.data.childrenList.forEach { displayDataTable(it) }
            ImGui.treePop()
        }
    }

    private fun displayDataTable(
        data: Environment.DataNode, extras: Map<String, String> = mapOf()
    ) {
        if (ImGui.beginTable("Data", 2)) {
            ImGui.tableSetupColumn("Property", ImGuiTableColumnFlags.WidthFixed)
            ImGui.tableSetupColumn("Value")
            ImGui.tableHeadersRow()

            for (extra in extras) {
                ImGui.tableNextRow()
                ImGui.tableSetColumnIndex(0)
                ImGui.textWrapped(extra.key)
                ImGui.tableSetColumnIndex(1)
                ImGui.textWrapped(extra.value)
            }

            for (dataEntry in data.dataMap) {
                ImGui.tableNextRow()
                ImGui.tableSetColumnIndex(0)
                ImGui.textWrapped(dataEntry.key.titlecase())
                ImGui.tableSetColumnIndex(1)
                ImGui.textWrapped(dataEntry.value)
            }

            ImGui.endTable()
        }
    }

    private fun pickDataSource() {
        when (dataSourcePickingStage) {
            DataSourcePickingStage.START -> window("Setup Data Source") {
                ImGui.text("Let's connect to a data source.")

                button("Use Recorded Data") {
                    dataSourcePickingStage = DataSourcePickingStage.RECORD
                }

                ImGui.sameLine()

                disabled {
                    button("Connect via Bluetooth")
                }
            }

            DataSourcePickingStage.RECORD -> filePicker(
                "Pick Recorded Data", FilePickerType.OneFile(), ".pandat"
            ) {
                val pick = it.values.first()

                try {
                    dataSource = RecordDataSource(Path(pick))
                } catch (e: InvalidProtocolBufferException) {
                    Marsh.show(Toast.Error("Recorded data file is malformed.").setException(e))
                    wantConnect = false
                }
            }

            DataSourcePickingStage.BLUETOOTH -> TODO()
        }
    }
}
