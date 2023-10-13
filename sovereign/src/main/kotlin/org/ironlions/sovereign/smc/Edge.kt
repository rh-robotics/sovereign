package org.ironlions.sovereign.smc

/** An edge in the state machine graph.
 * @param to The state to transition to.
 * @param callback The callback that decides if the transition should occur.*/
class Edge<T>(
    val to: Class<T>,
    val callback: ((state: State) -> Boolean)
)
