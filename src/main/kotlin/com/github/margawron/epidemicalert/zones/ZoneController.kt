package com.github.margawron.epidemicalert.zones

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class ZoneController(
    private val zoneService: ZoneService
) {

    @GetMapping(value = ["zone/{name}"])
    @PreAuthorize("@permissionEvaluator.isRegistered(authentication)")
    fun getZoneByName(@PathVariable name: String): ZoneDto {
        val zone = zoneService.findZoneByName(name)
        return ZoneDto.fromZone(zone)
    }

    @GetMapping(value = ["zone/"])
    @PreAuthorize("@permissionEvaluator.isRegistered(authentication)")
    fun getAllZones(): List<ZoneDto> {
        val zones = zoneService.findAllZones().toList()
        return zones.map { ZoneDto.fromZone(it) }
    }

    @PostMapping(value = ["zone/modify"])
    @PreAuthorize("@permissionEvaluator.isAdmin(authentication)")
    fun modifyZone(zone: ZoneDto): ZoneDto {
        val incomingZone = Zone.fromDto(zone)
        val savedZone = zoneService.saveModifiedZone(incomingZone)
        return ZoneDto.fromZone(savedZone)
    }

    @PostMapping(value = ["zones/modify"])
    @PreAuthorize("@permissionEvaluator.isAdmin(authentication)")
    fun modifyMultipleZones(zones: List<ZoneDto>): List<ZoneDto> {
        val incomingZones = zones.map { Zone.fromDto(it) }
        val savedZones = zoneService.saveModifiedZones(incomingZones)
        return savedZones.map{ ZoneDto.fromZone(it) }
    }
}