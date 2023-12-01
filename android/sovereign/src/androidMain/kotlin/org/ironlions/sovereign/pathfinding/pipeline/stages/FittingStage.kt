package org.ironlions.sovereign.pathfinding.pipeline.stages

import org.ironlions.common.geometry.Point
import org.ironlions.sovereign.pathfinding.environment.Environment
import org.ironlions.sovereign.pathfinding.fitting.DataFitterBuilder
import org.ironlions.sovereign.pathfinding.fitting.tree.TreeFitting

class FittingStage(
    environment: Environment.Builder,
    fitter: DataFitterBuilder,
) : PipelineStage<Point, TreeFitting> {
    /** The representation of the physical world. */
    val environment = environment.build()

    /** How we fit the environment into a form a pathfinder can use. */
    val fitter = fitter.build(this.environment)

    override fun cycle(goal: Point): TreeFitting = fitter.fit(goal)
}