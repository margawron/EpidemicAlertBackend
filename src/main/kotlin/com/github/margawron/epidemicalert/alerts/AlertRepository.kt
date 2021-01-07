package com.github.margawron.epidemicalert.alerts

import org.springframework.data.jpa.repository.JpaRepository

interface AlertRepository : JpaRepository<Alert, Long> {
}