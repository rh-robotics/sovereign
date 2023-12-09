package org.ironlions.panopticon.monitor

abstract class Watcher(private val monitored: Any) {
    /**
     * Clean the monitored class so it's no longer reported as dirty.
     */
    abstract fun reset()

    /**
     * Returns if the associated class has changed since the last reset.
     */
    abstract fun isDirty(): Boolean
}