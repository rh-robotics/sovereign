package org.ironlions.panopticon.client.event

/** Something that can receive an event from [EventDispatcher]. */
interface EventReceiver {
    /**
     * When receiving an event.
     *
     * @param event The event.
     */
    fun onEvent(event: Event)
}