package org.ironlions.panopticon.client.event

import org.ironlions.panopticon.client.Logging
import kotlin.reflect.KClass

/** Keeps track of event subscribers and dispatches new messages. */
class EventDispatcher {
    private val subscribers: MutableMap<KClass<out Event>, MutableList<EventReceiver>> = HashMap()

    /**
     * Broadcast an event to everyone willing to listen.
     *
     * @param event The event to broadcast. */
    fun broadcastToSubscribers(event: Event) =
        subscribers.getOrDefault(event::class, listOf()).forEach {
            it.onEvent(event)
        }

    /**
     * Subscribe to specific events. Prefer this at all costs.
     *
     * @param what The subscribing event receiver.
     * @param to The events in which to subscribe to.
     */
    fun subscribe(what: EventReceiver, to: List<KClass<out Event>>) {
        Logging.logger.debug { "EventReceiver '${what::class.simpleName}' is now listening to ${to.size} event${if (to.size != 1) "s" else ""}." }
        to.forEach {
            if (subscribers.containsKey(it)) subscribers[it]!!.add(what)
            else subscribers[it] = mutableListOf(what)
        }
    }

    /**
     * Subscribe to all events. Prefer subscribing to specific events at all costs.
     *
     * @param what The subscribing event receiver.
     */
    @Suppress("UNCHECKED_CAST")
    fun subscribe(what: EventReceiver) {
        Logging.logger.debug { "EventReceiver '${what::class.simpleName}' is now listening to all events." }
        Event::class.nestedClasses.forEach {
            assert(it.isInstance(Event::class))

            if (subscribers.containsKey(it)) subscribers[it]!!.add(what)
            else subscribers.put(it.java as KClass<out Event>, mutableListOf(what))
        }
    }

    /**
     * Unsubscribe from specific events.
     *
     * @param what The unsubscribing event receiver.
     * @param to The events in which to unsubscribe from.
     */
    fun unsubscribe(what: EventReceiver, from: List<KClass<out Event>>) = from.forEach {
        if (subscribers.containsKey(it)) {
            if (subscribers[it]!!.contains(what)) subscribers[it]!!.remove(what)
            else Logging.logger.warn { "Attempted to unsubscribe from not-subscribed-to event." }
        } else Logging.logger.warn { "Attempted to unsubscribe from not-subscribed-to event." }
    }

    /**
     * Unsubscribe from all events.
     *
     * @param what The unsubscribing event receiver.
     */
    fun unsubscribe(what: EventReceiver) = subscribers.forEach {
        it.value.remove(what)
    }
}