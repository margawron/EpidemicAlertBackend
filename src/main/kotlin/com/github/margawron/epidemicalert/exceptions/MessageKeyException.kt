package com.github.margawron.epidemicalert.exceptions

import kotlin.reflect.KClass

class MessageKeyException(originClass: KClass<*>, val messageKey: String, val args: Array<String>? = null) : Exception() {
    val originClass = originClass.simpleName
}