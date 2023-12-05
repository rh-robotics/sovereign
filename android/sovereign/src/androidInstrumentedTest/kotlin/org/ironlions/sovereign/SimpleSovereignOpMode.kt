package org.ironlions.sovereign

import org.ironlions.sovereign.opmode.OpModeType
import org.ironlions.sovereign.opmode.SovereignOpMode
import org.ironlions.sovereign.opmode.MakeAvailable
import org.ironlions.sovereign.opmode.SovereignOpModeProvider

@MakeAvailable(type = OpModeType.AUTON)
class SimpleSovereignOpMode(parent: SovereignOpModeProvider) : SovereignOpMode(parent) {
    override fun init() = TODO("Not yet implemented")
    override fun loop() = TODO("Not yet implemented")
}