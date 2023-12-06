package org.ironlions.panopticon.client.ui

import imgui.ImGui
import imgui.flag.ImGuiSelectableFlags
import imgui.flag.ImGuiTableColumnFlags
import imgui.flag.ImGuiTableFlags
import imgui.type.ImBoolean
import org.ironlions.common.components.Component
import org.ironlions.panopticon.client.data.DataTransceiver
import org.ironlions.panopticon.client.data.RecordedDataTransceiver
import org.ironlions.panopticon.client.render.Renderer
import org.ironlions.common.titlecase
import org.ironlions.ui.marsh.Marsh
import org.ironlions.ui.marsh.Toast
import kotlin.io.path.Path

private enum class DataSourcePickingStage {
    START, BLUETOOTH, RECORD
}

private open class ComponentDisplayProperty(val human: String) {
    class Uuid : ComponentDisplayProperty("UUID")
    class Region : ComponentDisplayProperty("Region")
    class Model : ComponentDisplayProperty("Model")
}

class Inspector : Window("Inspector") {
    private var dataSourcePickingStage = DataSourcePickingStage.START
    private var displayableComponentDisplayProperty: Map<ComponentDisplayProperty, ImBoolean> = mapOf(
        ComponentDisplayProperty.Uuid() to ImBoolean(false),
        ComponentDisplayProperty.Region() to ImBoolean(true),
        ComponentDisplayProperty.Model() to ImBoolean(false),
    )
    var wantConnect: Boolean = false
    var dataTransceiver: DataTransceiver? = null

    override fun content(renderer: Renderer) {
        // Easier development.
        if (dataTransceiver == null && System.getenv("PANOPTICON_PANDAT") != null) {
            dataTransceiver = RecordedDataTransceiver(Path(System.getenv("PANOPTICON_PANDAT")))
        }

        if (wantConnect) pickDataSource()
        if (dataTransceiver == null) {
            warningText("No data source!")
            return
        } else wantConnect = false

        ImGui.setNextItemOpen(true)
        if (ImGui.treeNode("Things")) {
            dataTransceiver!!.components().filterIsInstance<Component.Concrete>().forEach {
                if (ImGui.treeNodeEx(it.humanName)) {
                    displayComponentInfo(it)
                    ImGui.treePop()
                }
            }

            ImGui.treePop()
        }

        ImGui.spacing()
        ImGui.separator()
        ImGui.spacing()

        ImGui.setNextItemOpen(true)
        if (ImGui.treeNode("Filter")) {
            if (ImGui.beginTable(
                    "Filter",
                    2,
                    ImGuiTableFlags.Resizable or ImGuiTableFlags.NoSavedSettings or ImGuiTableFlags.Borders
                )
            ) {
                for (extra in displayableComponentDisplayProperty) {
                    ImGui.tableNextRow()
                    ImGui.tableNextColumn()
                    ImGui.selectable(
                        extra.key.human, extra.value, ImGuiSelectableFlags.SpanAllColumns
                    )

                    ImGui.tableNextColumn()
                    ImGui.text(if (extra.value.get()) "Shown" else "Hidden")
                }

                ImGui.endTable()
            }

            ImGui.treePop()
        }
    }

    private fun displayComponentInfo(component: Component.Concrete) {
        val extra = displayableComponentDisplayProperty.filter { it.value.get() }.keys.associate {
            it.human to when (it) {
                is ComponentDisplayProperty.Uuid -> component.uuid.toString()
                is ComponentDisplayProperty.Region -> "(${component.region.region.v1.x}, ${component.region.region.v1.y}, ${component.region.region.v1.z}) - (${component.region.region.v2.x}, ${component.region.region.v2.y}, ${component.region.region.v2.z})"
                is ComponentDisplayProperty.Model -> component.model.model
                else -> throw RuntimeException("Unreachable.")
            }
        }

        displayDataTable(component.adHocProperties, extra)

        /* if (thing.data_!!.children.isNotEmpty() && ImGui.treeNodeEx("Children")) {
            thing.data_!!.children.forEach { displayDataTable(it) }
            ImGui.treePop()
        } */
    }

    private fun displayDataTable(
        data: List<Component.Property.AdHoc>, extras: Map<String, String> = mapOf()
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

            for (dataEntry in data) {
                ImGui.tableNextRow()
                ImGui.tableSetColumnIndex(0)
                ImGui.textWrapped(dataEntry.data.first.titlecase())
                ImGui.tableSetColumnIndex(1)
                ImGui.textWrapped(dataEntry.data.second)
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
                    dataTransceiver = RecordedDataTransceiver(Path(pick))
                } catch (e: Exception) {
                    Marsh.show(Toast.Error("Recorded data file is malformed.").setException(e))
                    wantConnect = false
                }
            }

            DataSourcePickingStage.BLUETOOTH -> TODO()
        }
    }
}
