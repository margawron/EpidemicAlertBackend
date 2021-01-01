package com.github.margawron.epidemicalert.location

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
class LocationController(
    private val locationService: LocationService
) {

    @GetMapping("location")
    @PreAuthorize("@permissionEvaluator.isRegistered(authentication)")
    fun getNearbyLocations(@RequestParam("lat") latitude: Double, @RequestParam("lng")longitude: Double): List<LocationDto> {
        val locations = locationService.getLocationsFromLatLngInDistanceOf(latitude, longitude, 1000.0)
        return locations.map { LocationDto.fromEntity(it) }
    }

    @PostMapping("location")
    @PreAuthorize("@permissionEvaluator.isAdmin(authentication)")
    fun createNewLocation(@RequestBody dto: LocationDto): LocationDto {
        val savedLocation = locationService.createAndSaveLocationFromDto(dto)
        return LocationDto.fromEntity(savedLocation)
    }

    @DeleteMapping("location/{id}")
    @PreAuthorize("@permissionEvaluator.isAdmin(authentication)")
    fun removeLocation(@PathVariable("id") locationId: Long): ResponseEntity<Any> {
        locationService.deleteById(locationId)
        return ResponseEntity.ok().build()
    }
}