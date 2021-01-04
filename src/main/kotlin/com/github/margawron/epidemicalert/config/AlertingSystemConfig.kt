package com.github.margawron.epidemicalert.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
class AlertingSystemConfig {

    @Bean(name = ["alertingSystemTaskExecutor"])
    fun alertingSystemTaskExecutor(): ThreadPoolTaskExecutor{
        val threadPoolTaskExecutor = ThreadPoolTaskExecutor()
        threadPoolTaskExecutor.corePoolSize = 1
        threadPoolTaskExecutor.maxPoolSize = 1
        threadPoolTaskExecutor.keepAliveSeconds = 600
        threadPoolTaskExecutor.setQueueCapacity(100)
        return threadPoolTaskExecutor
    }
}