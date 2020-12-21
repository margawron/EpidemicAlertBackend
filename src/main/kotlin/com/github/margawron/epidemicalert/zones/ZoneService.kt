package com.github.margawron.epidemicalert.zones

import org.springframework.stereotype.Service

@Service
class ZoneService(
    private val zoneRepository: ZoneRepository
) {
    fun findZoneByName(name: String) = zoneRepository.findByName(name)

    fun findAllZones(): Iterable<Zone> = zoneRepository.findAll()
}