package com.github.margawron.epidemicalert.zones

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ZoneRepository : PagingAndSortingRepository<Zone, Long>{

    fun findByName(name: String): Zone?
}