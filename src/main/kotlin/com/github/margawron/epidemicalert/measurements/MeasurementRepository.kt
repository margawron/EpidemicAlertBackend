package com.github.margawron.epidemicalert.measurements

import com.github.margawron.epidemicalert.users.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface MeasurementRepository : JpaRepository<Measurement, Long> {

    // Most top
    fun findFirstByOwnerOfMeasurementAndTimestampAfterAndTimestampBeforeOrderByLatitudeDesc(
        ownerOfMeasurement: User,
        startOfDay: Instant,
        endOfDay: Instant
    ): Measurement?

    // Most right
    fun findFirstByOwnerOfMeasurementAndTimestampAfterAndTimestampBeforeOrderByLongitudeDesc(
        ownerOfMeasurement: User,
        startOfDay: Instant,
        endOfDay: Instant
    ): Measurement?

    // Most bottom
    fun findFirstByOwnerOfMeasurementAndTimestampAfterAndTimestampBeforeOrderByLatitudeAsc(
        ownerOfMeasurement: User,
        startOfDay: Instant,
        endOfDay: Instant
    ): Measurement?

    // Most left
    fun findFirstByOwnerOfMeasurementAndTimestampAfterAndTimestampBeforeOrderByLongitudeAsc(
        ownerOfMeasurement: User,
        startOfDay: Instant,
        endOfDay: Instant,
    ): Measurement?

    @Query("SELECT DISTINCT m.ownerOfMeasurement FROM Measurement m WHERE m.latitude < :trLat AND m.latitude > :blLat AND m.longitude < :trLng AND m.longitude > :blLng")
    fun findAllUsersInbounds(trLat: Double, trLng: Double, blLat: Double, blLng: Double): List<User>

    fun findAllByOwnerOfMeasurementAndTimestampAfterAndTimestampBeforeOrderByTimestampAsc(user: User, startInstant: Instant, endInstant: Instant, pageable: Pageable): Page<Measurement>
}