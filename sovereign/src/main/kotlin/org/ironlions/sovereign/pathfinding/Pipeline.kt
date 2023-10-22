package org.ironlions.sovereign.pathfinding

import org.ironlions.sovereign.pathfinding.environment.Environment
import org.ironlions.sovereign.pathfinding.environment.entities.Robot
import org.ironlions.sovereign.pathfinding.fitting.DataFitterBuilder
import org.ironlions.sovereign.pathfinding.fitting.DataFitter

/**
 * A pipeline for pathfinding.
 *
 * @param robot What we control.
 * @param environment Specifies how to build the [Environment].
 * @param dataFitter Specifies how to build the [DataFitter].
 */
class Pipeline(
    val robot: Robot,
    environment: Environment.Builder,
    dataFitter: DataFitterBuilder<*>
) {
    /** The representation of the physical world. */
    val environment = environment.build(robot)

    /** How we fit the environment into a form a pathfinder can use. */
    val dataFitter = dataFitter.build(this.environment)

    /**
     * Push one frame/tick through the pipeline.
     *
     * TODO: Make this work more like instruction pipelining in a CPU so we can compute multiple
     *  paths in parallel, thus increasing performance.
     *  [https://en.wikipedia.org/wiki/Instruction_pipelining]
     */
    fun loop() {
        dataFitter.fit()
    }
}