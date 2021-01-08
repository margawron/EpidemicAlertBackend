package com.github.margawron.epidemicalert.suspects

import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant

interface SuspectRepository : JpaRepository<Suspect, Long> {

    fun countAllByStartTimeAfterAndStartTimeBefore(after: Instant, before: Instant): Long

}