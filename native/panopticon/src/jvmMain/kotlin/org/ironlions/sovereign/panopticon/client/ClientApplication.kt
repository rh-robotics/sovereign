package org.ironlions.sovereign.panopticon.client

import org.apache.log4j.BasicConfigurator
import org.ironlions.sovereign.panopticon.client.render.Renderer
import org.ironlions.sovereign.panopticon.client.ui.Controls
import org.ironlions.sovereign.panopticon.client.ui.GraphicsScene
import org.ironlions.sovereign.panopticon.client.ui.Inspector
import org.ironlions.sovereign.panopticon.client.ui.Window
import kotlin.reflect.KClass

object ClientApplication {
    private val renderer: Renderer
    val windows: Map<KClass<*>, Window> =
        mapOf(
            Pair(GraphicsScene::class, GraphicsScene()),
            Pair(Inspector::class, Inspector()),
            Pair(Controls::class, Controls())
        )

    init {
        BasicConfigurator.configure()
        renderer = Renderer()
    }

    fun go() {
        Logging.logger.debug { "Starting main loop." }
        renderer.loop()
    }

    fun screechToAHalt() {
        Logging.logger.debug { "Cleaning up renderer." }
        renderer.destroy()
    }
}

fun main() {
    ClientApplication.go()
    ClientApplication.screechToAHalt()
}