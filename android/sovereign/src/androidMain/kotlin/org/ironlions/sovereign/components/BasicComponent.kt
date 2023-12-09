package org.ironlions.sovereign.components

import org.ironlions.common.graphics.Color
import org.ironlions.sovereign.opmode.OpModeProvider

/**
 * A basic component such as this can be interacted with as if you were programming an OpMode. All
 * your favorite functions that you've come to expect, such as `start`, `init`, `stop`, and so on
 * are all here! Ideal for teams just starting out, you won't have to deal with state machines or
 * commands. It basically mirrors the [OpMode API](https://ftctechnh.github.io/ftc_app/doc/javadoc/com/qualcomm/robotcore/eventloop/opmode/OpMode.html).
 *
 * The downside is, of course, you won't have the flexibility that commands or state machines offer.
 */
abstract class BasicComponent(
    parent: OpModeProvider,
    humanName: String,
    components: List<BasicComponent> = listOf(),
    properties: MutableList<Pair<String, String>> = mutableListOf(),
) : Component(parent, humanName, components, properties) {
    /**
     * A field thing that exists in space.
     *
     * @param humanName The human-friendly name of this object.
     * @param region The region that this object occupies.
     * @param color The color of this object.
     * @param model The model of this object (display only).
     * @param components Things that belong to this thing.
     * @param components Other properties of this object.
     */
    abstract class Concrete(
        parent: OpModeProvider,
        humanName: String,
        val region: org.ironlions.common.geometry.Region,
        val color: Color = Color(1f, 1f, 1f),
        val model: String = "bounding",
        components: List<BasicComponent> = listOf()
    ) : BasicComponent(parent, humanName, components)

    /**
     * User defined init_loop method. This method will be called repeatedly when the INIT button is
     * pressed. This method is optional. By default this method takes no action.
     */
    open fun initLoop() {}

    /**
     * User defined start method. This method will be called once when the PLAY button is first
     * pressed. This method is optional. By default this method takes not action.
     */
    open fun start() {}

    /**
     * User defined stop method
     *
     * This method will be called when this op mode is first disabled
     *
     * The stop method is optional. By default this method takes no action.
     */
    open fun stop() {}

    /**
     * User defined init method
     *
     * This method will be called once when the INIT button is pressed.
     */
    open fun init() {}

    /**
     * User defined loop method
     *
     * This method will be called repeatedly in a loop while this op mode is running
     */
    open fun loop() {}

    override fun initLoopWrapper() = initLoop()
    override fun startWrapper() = start()
    override fun stopWrapper() = stop()
    override fun initWrapper() = init()
    override fun loopWrapper() = loop()
}
