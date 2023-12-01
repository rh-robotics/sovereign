package org.ironlions.panopticon.monitor

/**
 * Tracks a monitored class for changes. The subclass, or something managing it, must explicitly set
 * the class as dirty or not.
 */
class ExplicitWatcher(monitored: Any) : Watcher(monitored) {
    private var dirty = false

    override fun reset() = run { dirty = false }
    override fun isDirty(): Boolean = dirty
}