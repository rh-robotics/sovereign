package org.ironlions.sovereign.geometry

import org.ironlions.sovereign.pathfinding.fitting.tree.TreeFitting

fun euclideanHeuristic(node: TreeFitting.Node, goal: TreeFitting.Node): Double =
    node.position.distanceTo(goal.position).millimeters