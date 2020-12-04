package com.github.margawron.epidemicalert.validation

import io.swagger.annotations.ApiModel

@ApiModel(value="Error Response")
class ErrorDto(
        val error: String?,
        val `object`: String?,
        val message: String?
)