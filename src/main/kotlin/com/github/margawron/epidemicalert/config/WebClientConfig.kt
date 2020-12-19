package com.github.margawron.epidemicalert.config

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController
import com.gargoylesoftware.htmlunit.WebClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebClientConfig {

    @Bean
    fun getWebClient(): WebClient {
        val webClient = WebClient()
        webClient.ajaxController = NicelyResynchronizingAjaxController()
        return webClient
    }
}