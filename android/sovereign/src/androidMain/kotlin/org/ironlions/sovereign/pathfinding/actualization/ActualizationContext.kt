package org.ironlions.sovereign.pathfinding.actualization

import com.acmerobotics.roadrunner.AccelConstraint
import com.acmerobotics.roadrunner.TrajectoryActionFactory
import com.acmerobotics.roadrunner.TurnActionFactory
import com.acmerobotics.roadrunner.TurnConstraints
import com.acmerobotics.roadrunner.VelConstraint

/**
 * Data needed to actualize a trajectory.
 *
 * @param eps Who knows what this is? Robot specific.
 * @param beginEndVel Who knows what this is? Robot specific.
 * @param baseTurnConstraints Who knows what this is? Robot specific.
 * @param baseVelConstraint Who knows what this is? Robot specific.
 * @param baseAccelConstraint Who knows what this is? Robot specific.
 * @param turnActionFactory A factory to create a turn actualization action. Robot specific.
 * @param trajectoryActionFactory A factory to create a trajectory actualization action. Robot specific.
 * @param dispResolution Who knows what this is? Robot specific.
 * @param angResolution Who knows what this is? Robot specific.
 */
data class ActualizationContext(
    val eps: Double,
    val beginEndVel: Double,
    val baseTurnConstraints: TurnConstraints,
    val baseVelConstraint: VelConstraint,
    val baseAccelConstraint: AccelConstraint,
    val turnActionFactory: TurnActionFactory,
    val trajectoryActionFactory: TrajectoryActionFactory,
    val dispResolution: Double,
    val angResolution: Double,
)
