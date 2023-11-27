package org.ironlions.sovereign.panopticon.client.render.imgui

import kotlin.io.path.Path
import com.google.protobuf.InvalidProtocolBufferException
import imgui.ImGui
import imgui.ImGui.sameLine
import imgui.ImGui.text
import org.ironlions.sovereign.panopticon.client.data.DataSource
import org.ironlions.sovereign.panopticon.client.data.RecordDataSource
import org.ironlions.sovereign.panopticon.client.render.Renderer
import org.ironlions.ui.marsh.Marsh
import org.ironlions.ui.marsh.Toast

private enum class DataSourcePickingStage {
    START, BLUETOOTH, RECORD
}

class Inspector : Window("Inspector") {
    private var dataSourcePickingStage = DataSourcePickingStage.START
    private var dataSource: DataSource? = null

    override fun content(renderer: Renderer) {
        if (dataSource == null) {
            warningText("No data source!")
            pickDataSource()
        } else {
            text("This is the inspector.")
        }
    }

    private fun pickDataSource() {
        when (dataSourcePickingStage) {
            DataSourcePickingStage.START -> modal("Setup Data Source") {
                text("Let's connect to a data source.")

                button("Use Recorded Data") {
                    dataSourcePickingStage = DataSourcePickingStage.RECORD
                }

                sameLine()

                disabled {
                    button("Connect via Bluetooth")
                }
            }

            DataSourcePickingStage.RECORD -> filePicker("Pick Recorded Data", FilePickerType.OneFile(), ".pandat") {
                val pick = it.values.first()

                try {
                    dataSource = RecordDataSource(Path(pick))
                } catch (e: InvalidProtocolBufferException) {
                    Marsh.show(Toast.Error("Recorded data file is malformed.").setException(e))
                    dataSourcePickingStage = DataSourcePickingStage.START
                }
            }

            DataSourcePickingStage.BLUETOOTH -> TODO()
        }
    }
}