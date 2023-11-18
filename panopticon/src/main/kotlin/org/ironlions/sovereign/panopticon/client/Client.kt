package org.ironlions.sovereign.panopticon.client

import org.apache.log4j.BasicConfigurator
import org.ironlions.sovereign.panopticon.client.render.Renderer

/** The panopticon client graphical user interface. */
class Client {

}

fun main() {
    BasicConfigurator.configure()

    val renderer = Renderer()
    Logging.logger.debug { "Starting main loop." }
    renderer.loop()
    Logging.logger.debug { "Cleaning up renderer." }
    renderer.destroy()
}