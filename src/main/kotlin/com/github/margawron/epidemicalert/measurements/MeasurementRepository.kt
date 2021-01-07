package com.github.margawron.epidemicalert.measurements

import com.github.margawron.epidemicalert.users.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface MeasurementRepository : JpaRepository<Measurement, Long> {

    // top-right measurement point on the map for user
    fun findFirstByOwnerOfMeasurementAndTimestampAfterAndTimestampBeforeOrderByLongitudeDescLatitudeDesc(
        ownerOfMeasurement: User,
        startOfDay: Instant,
        endOfDay: Instant
    ): Measurement?

    // bottom-left measurement point on the map for user
    fun findFirstByOwnerOfMeasurementAndTimestampAfterAndTimestampBeforeOrderByLongitudeAscLatitudeAsc(
        ownerOfMeasurement: User,
        startOfDay: Instant,
        endOfDay: Instant
    ): Measurement?

    @Query("SELECT DISTINCT m.ownerOfMeasurement FROM Measurement m WHERE m.latitude < :trLat AND m.latitude > :blLat AND m.longitude < :trLng AND m.longitude > :blLng")
    fun findAllUsersInbounds(trLat: Double, trLng: Double, blLat: Double, blLng: Double): List<User>

    fun findAllByOwnerOfMeasurementAndTimestampAfterAndTimestampBeforeOrderByTimestampAsc(user: User, startInstant: Instant, endInstant: Instant): MutableList<Measurement>
}