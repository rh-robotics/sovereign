package org.ironlions.sovereign

import org.ironlions.sovereign.components.Component
import org.ironlions.sovereign.opmode.MakeAvailable
import org.ironlions.sovereign.opmode.OpModeType
import org.ironlions.sovereign.opmode.OpModeProvider

@MakeAvailable(type = OpModeType.AUTON)
class Toothless(parent: OpModeProvider) : Component(parent, "Toothless") {
    override fun init() = TODO("Not yet implemented")
    override fun loop() = TODO("Not yet implemented")
}