package org.ironlions.sovereign.pathfinding.pipeline.stages

import com.acmerobotics.roadrunner.Action
import com.acmerobotics.roadrunner.Vector2d
import org.ironlions.sovereign.pathfinding.environment.things.Robot
import org.ironlions.sovereign.pathfinding.fitting.tree.TreeFitting

class InterpolationStage(
    private val robot: Robot
) : PipelineStage<List<TreeFitting.Node>, Action?> {
    override fun cycle(input: List<TreeFitting.Node>): Action? {
        if (robot.trajectoryActionBuilder == null) return null

        // TODO: This is horrifically basic and inefficient. But, it's a prototype, so hey!
        for (node in input) {
            robot.trajectoryActionBuilder.splineTo(
                Vector2d(
                    x = node.position.x.inches,
                    y = node.position.y.inches,
                ), 0.0
            )
        }

        return robot.trajectoryActionBuilder.build()
    }
}