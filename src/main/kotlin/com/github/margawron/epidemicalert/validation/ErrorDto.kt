package com.github.margawron.epidemicalert.validation

import io.swagger.annotations.ApiModel

@ApiModel(value = "Error Response")
class ErrorDto(val errorName: String?, val errorField: String?, val errorMessage: String?)