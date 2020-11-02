package com.github.margawron.epidemicalert.i18n

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource

@Configuration
class MessageSourceConfig {

    @Bean
    fun messageSource(): ReloadableResourceBundleMessageSource {
        return ReloadableResourceBundleMessageSource().apply {
            basenameSet.add("classpath:i18n/messages")
            setDefaultEncoding("UTF-8")
        }
    }
}