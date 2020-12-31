package com.github.margawron.epidemicalert.zones

import org.springframework.http.ResponseEntity
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
    fun getZoneByName(@PathVariable name: String): ResponseEntity<Zone> {
        val zone = zoneService.findZoneByName(name)
        return if (zone != null) ResponseEntity.ok(zone) else ResponseEntity.notFound().build()
    }

    @GetMapping(value = ["zone/"])
    @PreAuthorize("@permissionEvaluator.isRegistered(authentication)")
    fun getAllZones(): List<Zone> {
        return zoneService.findAllZones().toList()
    }

    @PostMapping(value = ["zone/modify"])
    @PreAuthorize("@permissionEvaluator.isAdmin(authentication)")
    fun modifyZone(zone: Zone): Zone {
        return zoneService.saveModifiedZone(zone)
    }

    @PostMapping(value = ["zones/modify"])
    @PreAuthorize("@permissionEvaluator.isAdmin(authentication)")
    fun modifyMultipleZones(zones: List<Zone>): List<Zone> {
        return zoneService.saveModifiedZones(zones)
    }
}