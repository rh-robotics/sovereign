package org.ironlions.sovereign.panopticon.client.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.isActive
import java.time.Instant

import org.ironlions.sovereign.common.proto.environment.Environment

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
     * Get all packets since the before the specified time.
     *
     * @param time The time to get all the packets before.
     */
    abstract fun since(time: Instant): List<Environment.Packet>?

    /** The timestamp for the last available packet. */
    abstract fun lastAvailable(): Instant
}