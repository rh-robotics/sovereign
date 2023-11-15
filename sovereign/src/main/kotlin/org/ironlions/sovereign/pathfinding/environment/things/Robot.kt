package org.ironlions.sovereign.pathfinding.environment.things

import com.acmerobotics.roadrunner.AccelConstraint
import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.TrajectoryActionFactory
import com.acmerobotics.roadrunner.TurnActionFactory
import com.acmerobotics.roadrunner.TurnConstraints
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.VelConstraint
import org.ironlions.sovereign.actualization.ActualizationContext
import org.ironlions.sovereign.geometry.Measurement
import org.ironlions.sovereign.geometry.Point
import org.ironlions.sovereign.geometry.Region
import org.ironlions.sovereign.geometry.Volume
import org.ironlions.sovereign.pathfinding.environment.ThingType
import org.ironlions.sovereign.pathfinding.environment.FieldThing

/**
 * A robot on the field.
 * TODO: Change volume and position to be accurate.
 *
 * @param position The position of the robot.
 */
class Robot(
    actualizationContext: ActualizationContext?,
    position: Point
) : FieldThing(
    Region(
        position, Volume(Measurement.Feet(3.0), Measurement.Feet(3.0), Measurement.Feet(3.0))
    ),
    ThingType.DYNAMIC,
) {
    val trajectoryActionBuilder: TrajectoryActionBuilder?

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
}