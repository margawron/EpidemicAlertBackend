package com.github.margawron.epidemicalert.exceptions

import kotlin.reflect.KClass

class KeyWithFieldException(
    originClass: KClass<*>,
    messageKey: String,
    args: Array<String>? = null,
    val field: String
) : KeyException(originClass, messageKey, args)