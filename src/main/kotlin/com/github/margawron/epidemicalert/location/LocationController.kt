package com.github.margawron.epidemicalert.location

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
class LocationController(
    private val locationService: LocationService
) {

    @GetMapping("location")
    @PreAuthorize("@permissionEvaluator.isRegistered(authentication)")
    fun getNearbyLocations(@RequestParam("lat") latitude: Double, @RequestParam("lng")longitude: Double): List<Location> {
        return locationService.getLocationsFromLatLngInDistanceOf(latitude, longitude, 1000.0)
    }

    @PostMapping("location")
    @PreAuthorize("@permissionEvaluator.isAtLeastModerator(authentication)")
    fun createNewLocation(@RequestBody location: Location): Location {
        // TODO
        return location
    }

}