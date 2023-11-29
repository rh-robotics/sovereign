package org.ironlions.sovereign.panopticon.client.util

import java.util.Locale

fun String.titlecase(): String = this.split(" ").joinToString(" ") { nit ->
    nit.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.ROOT
        ) else it.toString()
    }
}