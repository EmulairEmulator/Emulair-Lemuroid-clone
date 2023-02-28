package com.bigbratan.emulair.common.utils.kotlin

fun <T> lazySequenceOf(vararg producers: () -> T): Sequence<T> {
    return producers.asSequence()
        .map { it() }
}
