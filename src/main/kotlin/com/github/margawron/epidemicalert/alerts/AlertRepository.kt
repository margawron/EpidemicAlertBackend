package com.github.margawron.epidemicalert.alerts

import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant

interface AlertRepository : JpaRepository<Alert, Long> {

    fun countAlertsByCreationInstantAfterAndCreationInstantBefore(after: Instant, before: Instant): Long

}