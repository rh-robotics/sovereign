package org.ironlions.sovereign.pathfinding.environment

import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.Vector2d
import org.ironlions.sovereign.actualization.ActualizationContext
import org.ironlions.common.geometry.Measurement
import org.ironlions.common.geometry.Point
import org.ironlions.common.geometry.Region
import org.ironlions.common.geometry.Volume
import org.ironlions.common.environment.ThingType
import org.ironlions.common.environment.FieldThing
import org.ironlions.common.panopticon.proto.Thing
import java.util.UUID

/**
 * A robot on the field.
 *
 * TODO: Change volume and position to be accurate.
 *
 * @param position The position of the robot.
 */
class Robot(
    val name: String, actualizationContext: ActualizationContext?, position: Point
) {
    private val uuid = UUID.randomUUID()
    val trajectoryActionBuilder: TrajectoryActionBuilder?
    val region: Region = Region(
        position, Volume(Measurement.Feet(3.0), Measurement.Feet(3.0), Measurement.Feet(3.0))
    )

    init {
        trajectoryActionBuilder = if (actualizationContext != null) {
            TrajectoryActionBuilder(
                turnActionFactory = actualizationContext.turnActionFactory,
                trajectoryActionFactory = actualizationContext.trajectoryActionFactory,
                beginPose = Pose2d(Vector2d(0.0, 0.0), 0.0),
                eps = actualizationContext.eps,
                baseTurnConstraints = actualizationContext.baseTurnConstraints,
                baseVelConstraint = actualizationContext.baseVelConstraint,
                baseAccelConstraint = actualizationContext.baseAccelConstraint,
                resolution = actualizationContext.resolution
            )
        } else {
            null
        }
    }

    fun serialize(): Thing = TODO("Not yet implemented")
}