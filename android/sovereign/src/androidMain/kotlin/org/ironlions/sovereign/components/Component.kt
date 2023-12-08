package org.ironlions.sovereign.components

import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.ironlions.sovereign.opmode.OpModeProvider
import java.util.UUID

/**
 * Class that represents a thing on the field. You can think of this as an entity; it wasn't called
 * an entity because Teo objected and apparently he has power even though I do all the programming
 * for this. This code will be bundled with hundreds of other changes so he won't catch this comment
 * during review, I can be almost confident of that. Can you hear me Teo? No? Ha!
 *
 * You can think of this like a nestable OpMode: it essentially mirror an [FTC OpMode](https://github.com/OpenFTC/OpenRC-Turbo/blob/63035e7b37b2b27e9516538a7f35649fb6105f34/RobotCore/src/main/java/com/qualcomm/robotcore/eventloop/opmode/OpMode.java).
 *
 * @param humanName The name of this thing that is human readable.
 * @param components Things that belong to this time.
 * @param adHocProperties Other properties of this object.
 * @param uuid The UUID used by Panopticon and some internal parts of Sovereign. **Must be UNIQUE.**
 */
abstract class Component(
    protected val parent: OpModeProvider,
    val humanName: String,
    val components: List<Component> = listOf(),
    val adHocProperties: MutableList<Pair<String, String>> = mutableListOf(),
    val uuid: UUID = UUID.randomUUID(),
) {
    abstract class Property {
        class Region(val region: org.ironlions.common.geometry.Region) : Property()

        class Color(val r: Float, val g: Float, val b: Float) : Property()

        class Model(val model: String) : Property()

        var note: String? = null

        fun note(note: String) = apply { this.note = note }
    }

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
        val region: Property.Region,
        val color: Property.Color = Property.Color(1f, 1f, 1f),
        val model: Property.Model = Property.Model("bounding"),
        components: List<Component> = listOf()
    ) : Component(parent, humanName, components)

    /**
     * A field thing that does not exist in space.
     *
     * @param humanName The human-friendly name of this object.
     * @param components Things that belong to this thing.
     * @param components Other properties of this object.
     */
    abstract class Abstract(
        parent: OpModeProvider, humanName: String, components: List<Component> = listOf()
    ) : Component(parent, humanName, components)

    /** Gamepad 1 */
    val gamepad1: Gamepad? = parent.gamepad1

    /** Gamepad 2 */
    val gamepad2: Gamepad? = parent.gamepad2

    /**
     * Contains an object in which a user may accumulate data which is to be transmitted to the
     * driver station. This data is automatically transmitted to the driver station on a regular,
     * periodic basis.
     *
     * TODO: Integrate this with Panopticon.
     */
    val telemetry: Telemetry = parent.telemetry

    /**
     * Based on the configuration present on the driver station, this will contain hardware devices
     * that have been mapped.
     */
    val hardwareMap: HardwareMap? = parent.hardwareMap

    /**
     * The number of seconds this op mode has been running, this is updated before every call to
     * loop.
     */
    fun time() = parent.time

    /**
     * User defined init_loop method. This method will be called repeatedly when the INIT button is
     * pressed. This method is optional. By default this method takes no action.
     */
    open fun init_loop() = parent.init_loop()

    /**
     * User defined start method. This method will be called once when the PLAY button is first
     * pressed. This method is optional. By default this method takes not action.
     */
    open fun start() = parent.start()

    /**
     * User defined stop method
     *
     * This method will be called when this op mode is first disabled
     *
     * The stop method is optional. By default this method takes no action.
     */
    open fun stop() = parent.stop()

    /**
     * User defined init method
     *
     * This method will be called once when the INIT button is pressed.
     */
    abstract fun init()

    /**
     * User defined loop method
     *
     * This method will be called repeatedly in a loop while this op mode is running
     */
    abstract fun loop()

    /**
     * Requests that this OpMode be shut down if it the currently active opMode, much as if the stop
     * button had been pressed on the driver station; if this is not the currently active OpMode,
     * then this function has no effect. Note as part of this processing, the OpMode's [stop] method
     * will be called, as that is part of the usual shutdown logic. Note that this may be called
     * from *any thread.
     */
    fun requestOpModeStop() = parent.requestOpModeStop()

    /**
     * Immediately stops execution of the calling OpMode; and transitions to the STOP state.
     * No further code in the OpMode will execute once this has been called.
     */
    fun terminateOpModeNow() = parent.terminateOpModeNow()

    /**
     * Get the number of seconds this op mode has been running. This method has sub-millisecond
     * accuracy.
     *
     * @return The number of seconds this op mode has been running.
     */
    fun getRuntime() = parent.runtime

    /** Reset the runtime to zero. */
    fun resetRuntime() = parent.resetRuntime()

    /**
     * Refreshes the user's telemetry on the driver station with the contents of the provided
     * telemetry object if a nominal amount of time has passed since the last telemetry
     * transmission. Once transmitted, the contents of the telemetry object are (by default)
     * cleared.
     *
     * @param telemetry the telemetry data to transmit
     */
    fun updateTelemetry(telemetry: Telemetry) = parent.updateTelemetry(telemetry)

    /** Get some information about Sovereign, possible for filing a bug report. */
    fun sovereignInformation(): String =
        "Sovereign  Copyright (C) 2023  Milo Banks\nThis program comes with ABSOLUTELY NO WARRANTY.\nThis is free software, and you are welcome to\nredistribute it under certain conditions."

    /** Dump Soveriegn information to telemetry. */
    fun dumpSovereignInformation() = telemetry.addLine(sovereignInformation())
}
