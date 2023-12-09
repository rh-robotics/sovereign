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
 * @param humanName The name of this thing that is human readable.
 * @param components Things that belong to this time.
 * @param properties Other properties of this object.
 */
abstract class Component(
    protected val parent: OpModeProvider,
    protected val humanName: String,
    protected val components: List<BasicComponent> = listOf(),
    protected val properties: MutableList<Pair<String, String>> = mutableListOf()
) {
    protected val uuid: UUID = UUID.randomUUID()

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
     * The number of seconds this OpMode has been running. This is updated before every call to loop.
     */
    val time: Double
        get() {
            return parent.time;
        }

    /** Sovereign internal for [com.qualcomm.robotcore.eventloop.opmode.OpMode.init_loop]. */
    abstract fun initLoopWrapper()

    /** Sovereign internal for [com.qualcomm.robotcore.eventloop.opmode.OpMode.start]. */
    abstract fun startWrapper()

    /** Sovereign internal for [com.qualcomm.robotcore.eventloop.opmode.OpMode.stop]. */
    abstract fun stopWrapper()

    /** Sovereign internal for [com.qualcomm.robotcore.eventloop.opmode.OpMode.init]. */
    abstract fun initWrapper()

    /** Sovereign internal for [com.qualcomm.robotcore.eventloop.opmode.OpMode.loop]. */
    abstract fun loopWrapper()

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

    /** Dump Sovereign information to telemetry. */
    fun dumpSovereignInformation() = telemetry.addLine(sovereignInformation())
}