package com.github.margawron.epidemicalert.exceptions

import kotlin.reflect.KClass

class ErrorCodeWithFieldException(
    originClass: KClass<*>,
    messageKey: String,
    args: Array<String>? = null,
    val field: String
) : ErrorCodeException(originClass, messageKey, args)