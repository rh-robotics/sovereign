package org.ironlions.sovereign.panopticon.client.render.event

interface EventReceiver {
    fun onEvent(event: Event)
}