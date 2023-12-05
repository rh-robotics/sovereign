package org.ironlions.sovereign.opmode

import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry

/**
 * A Sovereign OpMode that essentially mirrors the FTC OpMode
 * [here](https://github.com/OpenFTC/OpenRC-Turbo/blob/63035e7b37b2b27e9516538a7f35649fb6105f34/RobotCore/src/main/java/com/qualcomm/robotcore/eventloop/opmode/OpMode.java).
 *
 * @param parent The actual OpMode backing this OpMode. If you need anything else, check here.
 */
@Suppress("FunctionName")
abstract class SovereignOpMode(protected val parent: SovereignOpModeProvider) {
    /** Gamepad 1 */
    val gamepad1: Gamepad = parent.gamepad1

    /** Gamepad 2 */
    val gamepad2: Gamepad = parent.gamepad2

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
    val hardwareMap: HardwareMap = parent.hardwareMap

    /**
     * The number of seconds this op mode has been running, this is updated before every call to
     * loop.
     */
    var time = parent.time

    /**
     * User defined init_loop method. This method will be called repeatedly when the INIT button is
     * pressed. This method is optional. By default this method takes no action.
     */
    open fun init_loop() {}

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
}