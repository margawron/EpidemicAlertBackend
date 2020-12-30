package com.github.margawron.epidemicalert.pathogens

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


@Repository
interface PathogenRepository : JpaRepository<Pathogen, Long> {

    fun findByName(name: String): Pathogen?

    fun findByNameContainingIgnoreCase(name: String): List<Pathogen>
}