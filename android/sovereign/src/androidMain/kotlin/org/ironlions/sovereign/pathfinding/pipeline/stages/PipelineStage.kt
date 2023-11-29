package org.ironlions.sovereign.pathfinding.pipeline.stages

interface PipelineStage<I, O> {
    /** Preform a cycle of the pipeline stage. */
    fun cycle(input: I): O
}