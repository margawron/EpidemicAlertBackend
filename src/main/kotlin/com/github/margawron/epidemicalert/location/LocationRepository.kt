package com.github.margawron.epidemicalert.location

import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant

interface LocationRepository : JpaRepository<Location, Long> {

    fun findAllByLatitudeLessThanEqualAndLatitudeGreaterThanEqualAndLongitudeLessThanEqualAndLongitudeGreaterThanEqualAndExpiryDateBeforeOrExpiryDateIsNull(maxLat: Double, minLat: Double, maxLng: Double, minLng: Double, expiryDate: Instant): List<Location>
}