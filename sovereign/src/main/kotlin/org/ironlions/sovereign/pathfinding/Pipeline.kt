package org.ironlions.sovereign.pathfinding

import org.ironlions.sovereign.pathfinding.algorithms.Pathfinder
import org.ironlions.sovereign.pathfinding.algorithms.PathfinderBuilder
import org.ironlions.sovereign.pathfinding.environment.Environment
import org.ironlions.sovereign.pathfinding.environment.things.Robot
import org.ironlions.sovereign.pathfinding.fitting.DataFitterBuilder
import org.ironlions.sovereign.pathfinding.fitting.DataFitter
import org.ironlions.sovereign.pathfinding.fitting.FittingResult

/**
 * A pipeline for pathfinding.
 *
 * @param robot What we control.
 * @param environment Specifies how to build the [Environment].
 * @param dataFitter Specifies how to build the [DataFitter].
 * @param pathfinder Specifies how to build the [Pathfinder].
 */
class Pipeline<R : FittingResult>(
    val robot: Robot,
    environment: Environment.Builder,
    dataFitter: DataFitterBuilder<R>,
    pathfinder: PathfinderBuilder<R>
) {
    /** The representation of the physical world. */
    val environment = environment.build(robot)

    /** How we fit the environment into a form a pathfinder can use. */
    val dataFitter = dataFitter.build(this.environment)

    /** The actual pathfinder. */
    val pathfinder = pathfinder.build()

    /**
     * Push one frame/tick through the pipeline.
     *
     * TODO: Make this work more like instruction pipelining in a CPU so we can compute multiple
     *  paths in parallel, thus increasing performance.
     *  [https://en.wikipedia.org/wiki/Instruction_pipelining]
     */
    fun loop() {
        dataFitter.fit()
        pathfinder.pathfind(dataFitter.get())
        // TODO: Road Runner from pathfinding.
    }
}