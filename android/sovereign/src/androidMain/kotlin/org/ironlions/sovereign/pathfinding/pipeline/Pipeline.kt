package org.ironlions.sovereign.pathfinding.pipeline

import com.acmerobotics.roadrunner.Action
import org.ironlions.common.geometry.Point
import org.ironlions.sovereign.pathfinding.algorithms.Pathfinder
import org.ironlions.sovereign.pathfinding.algorithms.PathfinderBuilder
import org.ironlions.sovereign.pathfinding.environment.Environment
import org.ironlions.sovereign.pathfinding.fitting.DataFitterBuilder
import org.ironlions.sovereign.pathfinding.fitting.DataFitter
import org.ironlions.sovereign.pathfinding.pipeline.stages.*

/**
 * A pipeline for pathfinding.
 *
 * @param environment Specifies how to build the [Environment].
 * @param fitter Specifies how to build the [DataFitter].
 * @param pathfinder Specifies how to build the [Pathfinder].
 */
class Pipeline(
    environment: Environment.Builder, fitter: DataFitterBuilder, pathfinder: PathfinderBuilder
) {
    /** The fitting stage of the pipeline. */
    internal val fitter = FittingStage(environment, fitter)

    /** The pathfinding stage of the pipeline. */
    private val pathfinder = PathfindingStage(pathfinder)

    /** The interpolation stage of the pipeline. */
    private val interpolator = InterpolationStage(environment.robot)

    /**
     * Push one frame/tick through the pipeline.
     * TODO: Parallelize.
     *
     * @param goal Where to pathfind to.
     */
    fun cycle(goal: Point): Action? = pathfinder.cycle(fitter.cycle(goal))?.let {
        return interpolator.cycle(it)
    }

    /**
     * Flush the pipeline like an IV.
     *
     * @param goal Where to pathfind to.
     */
    fun flush(goal: Point): Action? =
        generateSequence { cycle(goal) }.firstNotNullOfOrNull { it }
}