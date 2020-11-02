package com.github.margawron.epidemicalert

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EpidemicAlertApplication

fun main(args: Array<String>) {
    runApplication<EpidemicAlertApplication>(*args)
}
