package com.github.margawron.epidemicalert.zones

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class ZoneController(
    private val zoneService: ZoneService
) {

    @GetMapping(value = ["zones"])
    @PreAuthorize("@permissionEvaluator.isUser(authentication)")
    fun getZones(): ResponseEntity<Any> {
        return ResponseEntity.ok(zoneService.redZones)
    }
}