package org.ironlions.sovereign.pathfinding.pipeline.stages

import org.ironlions.sovereign.pathfinding.algorithms.PathfinderBuilder
import org.ironlions.sovereign.pathfinding.fitting.tree.TreeFitting

class PathfindingStage(
    pathfinder: PathfinderBuilder
) : PipelineStage<TreeFitting, List<TreeFitting.Node>?> {
    /** The actual pathfinder. */
    val pathfinder = pathfinder.build()

    override fun cycle(input: TreeFitting): List<TreeFitting.Node>? = pathfinder.pathfind(input)
}