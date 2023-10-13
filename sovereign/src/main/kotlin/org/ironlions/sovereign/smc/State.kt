package org.ironlions.sovereign.smc

/**
 * A state, belonging to an abstract state machine.
 */
interface State {
    /** The edges of the current state.  */
    val edges: Array<Edge<*>>

    /**
     * Runs ONCE during state machine initialization.
     *
     *
     * Sets up the robot physically, digitally, and mentally for the trials ahead.
     */
    fun init() {
        /* Do nothin'. */
    }

    /**
     * Runs ONCE when the state is very first reached. Very little initialization should be done
     * here, if any.
     */
    fun start() {
        /* Do nothin'. */
    }

    /**
     * Code that is called once per frame if the state is active. Process input, send output to
     * motors, etc. Do it all here!
     */
    fun loop() {
        /* Do nothin'. */
    }
}

/**
 * Describes some metadata about a state.
 * @param color The color to use when graphing with GraphViz DOT.
 * @param role How to use the state.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class StateMeta(
    val color: String = "#000000",
    val role: StateRole = StateRole.TRANSITIONAL
)

/**
 * How to use the state; initial, transitional, or terminating.
 */
enum class StateRole {
    /** This is the first state to jump to.  */
    INITIAL,

    /** This state is nothing special in the eyes of the controller.  */
    TRANSITIONAL,

    /** Once this state is run once, the state machine dies.  */
    FINAL;
}