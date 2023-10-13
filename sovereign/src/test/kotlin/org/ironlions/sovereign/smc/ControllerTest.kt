package org.ironlions.sovereign.smc

import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class ControllerTest {
    @Test
    fun duplicateStateBehaviour_isCorrect() {
        val exception = assertThrows(ConstructionException::class.java) {
            Controller(
                arrayOf(IdleState(), IdleState(), ForwardState())
            )
        }

        assertTrue(exception.message!!.contains("already included in state machine"))
    }

    @Test
    fun noStateBehaviour_isCorrect() {
        val exception = assertThrows(ConstructionException::class.java) {
            Controller(arrayOf())
        }

        assertTrue(exception.message!!.contains("no states in state machine"))
    }

    @Test
    fun noInitialStateBehaviour_isCorrect() {
        val exception = assertThrows(ConstructionException::class.java) {
            Controller(arrayOf(ForwardState(), BackwardState()))
        }

        assertTrue(exception.message!!.contains("no INITIAL state was supplied"))
    }

    @Test
    fun noAnnotationBehaviour_isCorrect() {
        val exception = assertThrows(ConstructionException::class.java) {
            Controller(arrayOf(ForwardState(), IdleState(), NoAnnotationState()))
        }

        assertTrue(exception.message!!.contains("isn't annotated with StateMeta"))
    }

    @Test
    fun saneBehaviour_isCorrect() {
        assertNotNull {
            Controller(arrayOf(ForwardState(), IdleState()))
        }
    }
}

@StateMeta(role = StateRole.INITIAL)
private class IdleState() : State {
    override val edges: Array<Edge<*>> = arrayOf(Edge(ForwardState::class.java) {
        true
    })
}

@StateMeta(role = StateRole.TRANSITIONAL)
private class ForwardState : State {
    override val edges: Array<Edge<*>> = arrayOf(Edge(IdleState::class.java) {
        true
    })
}

@StateMeta(role = StateRole.TRANSITIONAL)
private class BackwardState : State {
    override val edges: Array<Edge<*>> = arrayOf(Edge(IdleState::class.java) {
        true
    })
}

private class NoAnnotationState : State {
    override val edges: Array<Edge<*>> = arrayOf(Edge(IdleState::class.java) {
        true
    })
}
