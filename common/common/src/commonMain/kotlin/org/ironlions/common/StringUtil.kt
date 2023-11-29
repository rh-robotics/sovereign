package org.ironlions.common

import java.util.Locale

/** Converts a string, split by spaces, into a title cased string. */
fun String.titlecase(): String = this.split(" ").joinToString(" ") { nit ->
    nit.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.ROOT
        ) else it.toString()
    }
}

/** Lowercases a string. */
fun String.lowercase(): String = this.split(" ").joinToString(" ") { nit ->
    nit.replaceFirstChar {
        if (it.isUpperCase()) it.lowercase(
            Locale.ROOT
        ) else it.toString()
    }
}