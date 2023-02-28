package com.bigbratan.emulair.common.utils.kotlin

inline fun <T> T?.filterNullable(predicate: (T) -> Boolean): T? {
    return if (this != null && predicate(this)) {
        this
    } else {
        null
    }
}
