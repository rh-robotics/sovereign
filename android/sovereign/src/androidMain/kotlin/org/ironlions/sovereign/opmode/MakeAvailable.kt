package org.ironlions.sovereign.opmode

/** The type of OpMode to generate code for. */
enum class OpModeType {
    /** An OpMode that is specialized to be controlled by a driver. */
    TELEOP,
    /** An OpMode that is specialized to not be controlled by a driver. z*/
    AUTON
}

/**
 * An OpMode that does some special Sovereign specific goo and registers itself with the requisite
 * FTC systems for a successful boot. This isn't required; you could technically inline all the
 * bootstrap and boilerplate, but this isn't recommended because:
 *
 * 1. it's liable to change at any time.
 * 2. it's simpler.
 *
 * If you need something, file a bug report, I'll be happy to help you out :)
 *
 * @param type The type of the OpMode.
 * @param group The group the OpMode belongs to. Overrides the default value of the package.
 * @param name The name of the OpMode. Overrides the default value of the class name.
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
annotation class MakeAvailable(
    val type: OpModeType, val group: String = "", val name: String = ""
)
