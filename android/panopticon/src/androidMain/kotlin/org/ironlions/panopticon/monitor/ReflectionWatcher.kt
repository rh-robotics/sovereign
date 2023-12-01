package org.ironlions.panopticon.monitor

import kotlin.RuntimeException

/**
 * Tracks a monitored class for changes via reflection. No work has to be done on the subclass's
 * part, but this will likely eat a bunch of memory and be sorta slow. If you don't care about that,
 * use this class. Otherwise, consider marking your object as dirty explicitly.
 *
 * This class is not complete, usage will result in a [RuntimeException].
 *
 * TODO: Time will tell how performant this is. If it's slow enough, we may want to move this into
 *  an annotation processor.
 */
@Deprecated("This class does not work, currently, and may be flawed in concept.", level = DeprecationLevel.ERROR)
class ReflectionWatcher(monitored: Any) : Watcher(monitored) {
    init {
        throw RuntimeException("How did you get in here!?")
    }

    override fun reset() = TODO("Not yet implemented")
    override fun isDirty(): Boolean = TODO("Not yet implemented")
}

/* class MonitoredWatcher(private val type: Class<*>, private val monitored: Any) : Watcher(monitored) {
    private var fields: MutableMap<Field, Pair<Int, MonitoredWatcher>> = mutableMapOf()

    init {
        reset()
    }

    /**
     * Clean the monitored class so it's no longer reported as dirty.
     */
    override fun reset() {
        fields = type.fields.associateWith {
            val field = it.get(monitored)
                ?: throw RuntimeException("Monitored class lied about field '${it.name}'.")

            println("Resetting ${it.declaringClass} ${it.name}...")

            Pair(field.hashCode(), MonitoredWatcher(it.declaringClass, field))
        }.toMutableMap()
    }

    /**
     * Returns if the associated class has changed since the last reset.
     */
    override fun isDirty(): Boolean = if (fields.isEmpty()) false else !fields.all { (rField, tracked) ->
        val field = rField.get(monitored)
            ?: throw RuntimeException("Monitored class no longer has field '${rField.name}'.")

        if (field.hashCode() != tracked.first) return false // Initial equivalency check for speed.
        else return tracked.second.isClean() // First equivalency check failed, make sure by walking down.
    }
} */