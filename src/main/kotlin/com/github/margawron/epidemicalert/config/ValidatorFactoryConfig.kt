package com.github.margawron.epidemicalert.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

@Configuration
class ValidatorFactoryConfig {

    @Bean
    fun getValidator(messageSource: MessageSource): LocalValidatorFactoryBean {
        return LocalValidatorFactoryBean().apply {
            setValidationMessageSource(messageSource)
        }
    }

}