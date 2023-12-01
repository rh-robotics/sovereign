package org.ironlions.sovereign.pathfinding.fitting

import com.acmerobotics.roadrunner.AccelConstraint
import com.acmerobotics.roadrunner.TurnActionFactory
import com.acmerobotics.roadrunner.TurnConstraints
import com.acmerobotics.roadrunner.VelConstraint
import org.ironlions.common.geometry.Measurement
import org.ironlions.common.geometry.Point
import org.ironlions.common.seasons.PrototypicalField
import org.ironlions.sovereign.pathfinding.environment.Environment
import org.ironlions.sovereign.pathfinding.environment.things.Robot
import org.ironlions.sovereign.pathfinding.fitting.tree.grid.GridTreeFitting
import org.junit.Before

class GridTreeFittingTest {
    private lateinit var gridTreeFittingResult: GridTreeFitting

    @Before
    fun setUp() {
        val environment = Environment.Builder(
            Robot(
                actualizationContext = null, Point(
                    Measurement.Millimeters(0.0), Measurement.Millimeters(0.0)
                )
            )
        ).build()
        val grid = PrototypicalField.grid.reExpress()
        gridTreeFittingResult = GridTreeFitting(environment, grid)
    }
}