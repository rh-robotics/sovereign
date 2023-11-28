package org.ironlions.sovereign.panopticon.client.render.imgui

import kotlin.io.path.Path
import com.google.protobuf.InvalidProtocolBufferException
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
    var dataSource: DataSource? = null
    var wantConnect: Boolean = false

    override fun content(renderer: Renderer) {
        if (wantConnect) pickDataSource()
        if (dataSource == null) {
            warningText("No data source!")
            return
        } else wantConnect = false

        text(dataSource.toString())
    }

    private fun pickDataSource() {
        when (dataSourcePickingStage) {
            DataSourcePickingStage.START -> window("Setup Data Source") {
                text("Let's connect to a data source.")

                button("Use Recorded Data") {
                    dataSourcePickingStage = DataSourcePickingStage.RECORD
                }

                sameLine()

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