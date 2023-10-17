package org.ironlions.sovereign.smc

import org.junit.Assert
import org.junit.Test

class ControllerTest {
    @Test
    fun duplicateStateBehaviour_isCorrect() {
        val exception = Assert.assertThrows(ConstructionException::class.java) {
            Controller(
                arrayOf(IdleState(), IdleState(), ForwardState())
            )
        }

        Assert.assertTrue(exception.message!!.contains("already included in state machine"))
    }

    @Test
    fun noStateBehaviour_isCorrect() {
        val exception = Assert.assertThrows(ConstructionException::class.java) {
            Controller(arrayOf())
        }

        Assert.assertTrue(exception.message!!.contains("no states in state machine"))
    }

    @Test
    fun noInitialStateBehaviour_isCorrect() {
        val exception = Assert.assertThrows(ConstructionException::class.java) {
            Controller(arrayOf(ForwardState(), BackwardState()))
        }

        Assert.assertTrue(exception.message!!.contains("no INITIAL state was supplied"))
    }

    @Test
    fun noAnnotationBehaviour_isCorrect() {
        val exception = Assert.assertThrows(ConstructionException::class.java) {
            Controller(arrayOf(ForwardState(), IdleState(), NoAnnotationState()))
        }

        Assert.assertTrue(exception.message!!.contains("isn't annotated with StateMeta"))
    }

    @Test
    fun saneBehaviour_isCorrect() {
        Assert.assertNotNull {
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
