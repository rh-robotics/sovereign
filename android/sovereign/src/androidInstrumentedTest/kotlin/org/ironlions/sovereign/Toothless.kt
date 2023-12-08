package org.ironlions.sovereign

import org.ironlions.sovereign.components.Component
import org.ironlions.sovereign.opmode.MakeAvailable
import org.ironlions.sovereign.opmode.OpModeType
import org.ironlions.sovereign.opmode.OpModeProvider

@MakeAvailable(type = OpModeType.AUTON)
class Toothless(parent: OpModeProvider) : Component(parent, "Toothless") {
    private var lastTime: Double = 0.0
    private var deltaTime: Double = 0.0

    override fun init() {
        lastTime = time()
        dumpSovereignInformation()
    }

    override fun loop() {
        deltaTime = time() - lastTime

        telemetry.addData("deltaTime", deltaTime)
        lastTime = time()
    }
}