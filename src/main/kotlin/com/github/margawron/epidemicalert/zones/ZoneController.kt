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

    @GetMapping(value = ["zones/{name}"])
    @PreAuthorize("@permissionEvaluator.isUser(authentication)")
    fun getZoneByName(@PathVariable name: String): ResponseEntity<Zone> {
        val zone = zoneService.findZoneByName(name) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok().body(zone)
    }

    @GetMapping(value = ["zones/"])
    @PreAuthorize("@permissionEvaluator.isUser(authentication)")
    fun getAllZones(): ResponseEntity<List<Zone>> {
        return ResponseEntity.ok().body(zoneService.findAllZones().toList())
    }

    @PostMapping(value = ["zones/modify"])
    @PreAuthorize("@permissionEvaluator.isAdmin(authentication)")
    fun modifyZone(zone: Zone): ResponseEntity<Any> {
        zoneService.saveModifiedZone(zone)
        return ResponseEntity.ok().build()
    }
}