package com.bigbratan.emulair.common.utils.kotlin

fun String.startsWithAny(strings: Collection<String>) = strings.any { this.startsWith(it) }
