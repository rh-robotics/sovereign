package org.ironlions.sovereign.panopticon.client.render.event

/** Keeps track of event subscribers and dispatches new messages. */
class EventDispatcher {
    private val subscribers: MutableList<EventReceiver> = mutableListOf()

    /** Broadcast an event to everyone willing to listen. */
    fun broadcastToSubscribers(event: Event) = subscribers.forEach { it.onEvent(event) }

    /** Subscribe! */
    fun subscribe(what: EventReceiver) = subscribers.add(what)

    /** Unsubscribe :( */
    fun unsubscribe(what: EventReceiver) = subscribers.remove(what)
}