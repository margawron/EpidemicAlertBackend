package com.github.margawron.epidemicalert.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMapping
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfig {

    @Bean
    fun api(): Docket =
        Docket(DocumentationType.SWAGGER_2)
            .host("https://ostrzezenieepidemiczne.tk/api/")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.github.margawron.epidemicalert"))
            .apis(RequestHandlerSelectors.withMethodAnnotation(RequestMapping::class.java))
            .paths(PathSelectors.any())
            .build()
}