package com.github.margawron.epidemicalert.location

import org.springframework.data.jpa.repository.JpaRepository

interface LocationRepository : JpaRepository<Location, Long> {

    fun findAllByLatitudeLessThanEqualAndLatitudeGreaterThanEqualAndLongitudeLessThanEqualAndLongitudeGreaterThanEqual(maxLat: Double, minLat: Double, maxLng: Double, minLng: Double): List<Location>
}