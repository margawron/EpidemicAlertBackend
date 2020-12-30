package com.github.margawron.epidemicalert.zones

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ZoneRepository : JpaRepository<Zone, Long> {

    fun findByName(name: String): Zone?
}