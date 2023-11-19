package org.ironlions.sovereign.panopticon.client.render.event

import org.ironlions.sovereign.panopticon.client.Logging
import kotlin.reflect.KClass

/** Keeps track of event subscribers and dispatches new messages. */
class EventDispatcher {
    private val subscribers: MutableMap<KClass<out Event>, MutableList<EventReceiver>> = HashMap()

    /** Broadcast an event to everyone willing to listen. */
    fun broadcastToSubscribers(event: Event) =
        subscribers.getOrDefault(event::class, listOf()).forEach {
            it.onEvent(event)
        }

    /** Subscribe to specific events. Prefer this at all costs. */
    fun subscribe(what: EventReceiver, to: List<KClass<out Event>>) = to.forEach {
        if (subscribers.containsKey(it)) subscribers[it]!!.add(what)
        else subscribers[it] = mutableListOf(what)
    }

    /** Subscribe to all events. */
    @Suppress("UNCHECKED_CAST")
    fun subscribe(what: EventReceiver) = Event::class.nestedClasses.forEach {
        assert(it.isInstance(Event::class))
        if (subscribers.containsKey(it)) subscribers[it]!!.add(what)
        else subscribers.put(it.java as KClass<out Event>, mutableListOf(what))
    }

    /** Unsubscribe from specific events. */
    fun unsubscribe(what: EventReceiver, from: List<KClass<out Event>>) = from.forEach {
        if (subscribers.containsKey(it)) {
            if (subscribers[it]!!.contains(what)) subscribers[it]!!.remove(what)
            else Logging.logger.warn { "Attempted to unsubscribe from not-subscribed-to event." }
        } else Logging.logger.warn { "Attempted to unsubscribe from null event type." }
    }

    /** Unsubscribe from all events. */
    fun unsubscribe(what: EventReceiver) = subscribers.forEach {
        it.value.remove(what)
    }
}