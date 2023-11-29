package org.ironlions.sovereign.panopticon.client.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.isActive
import org.ironlions.proto.panopticon.environment.Thing

/** A source of data from the robot. */
abstract class DataSource : CoroutineScope by MainScope() {
    private var job: Job? = null

    /** Start listening on the data source. */
    fun startListening() {
        job = launch {
            while (isActive) listenFrame()
        }
    }

    /** Stop listening on the data source. */
    fun stopListening() = job?.cancel()!!

    /** Do a bit of listening. */
    abstract fun listenFrame()

    /**
     * Advance the cursor one space. If there is no space to move forward, it will do nothing.
     */
    abstract fun next()

    /**
     * Move the cursor one back. If there is no space to move back, it will do nothing.
     */
    abstract fun prior()

    /**
     * Get all the things on the field at the current cursor.
     */
    abstract fun things(): List<Thing>

    /** The number of packets available. */
    abstract fun size(): Int
}