package com.github.margawron.epidemicalert.suspects

import org.springframework.data.jpa.repository.JpaRepository

interface SuspectRepository : JpaRepository<Suspect, Long> {

}