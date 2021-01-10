package com.github.margawron.epidemicalert.alerts

import com.github.margawron.epidemicalert.exceptions.ErrorCodeException
import com.github.margawron.epidemicalert.proximity.ProximityMeasurementDto
import com.github.margawron.epidemicalert.proximity.ProximityType
import com.github.margawron.epidemicalert.suspects.SuspicionLevel
import com.github.margawron.epidemicalert.users.User
import java.time.Instant
import kotlin.jvm.Throws

data class AlertDto(
    var id: Long,
    var proximityType: ProximityType,
    var suspicionLevel: SuspicionLevel,
    var pathogenId: Long,
    var victimId: Long,
    var victimMeasurements: List<ProximityMeasurementDto>,
    var suspectId: Long,
    var suspectMeasurements: List<ProximityMeasurementDto>,
    var alertCreationInstant: Instant,
){
    companion object{

        @Throws(ErrorCodeException::class)
        fun fromEntityForUser(alert: Alert, user: User) : AlertDto {
            val id = alert.id!!
            val suspect = alert.suspect
            val suspectedUser = suspect.suspect
            val victimUser = alert.victim
            val proximityType: ProximityType = when {
                victimUser.id == user.id -> {
                    ProximityType.VICTIM
                }
                suspectedUser.id == user.id -> {
                    ProximityType.SUSPECT
                }
                else -> {
                    throw ErrorCodeException(User::class, "user.credentials.refused")
                }
            }
            val proximityMeasurements = alert.proximityMeasurements
            val victimMeasurements = proximityMeasurements
                .filter { it.proximityType == ProximityType.VICTIM }
                .map { ProximityMeasurementDto.fromMeasurement(it) }
            val suspectMeasurements = proximityMeasurements
                .filter { it.proximityType == ProximityType.SUSPECT }
                .map { ProximityMeasurementDto.fromMeasurement(it) }

            return AlertDto(
                id,
                proximityType,
                suspect.suspicionLevel,
                suspect.pathogen.id!!,
                victimUser.id!!,
                victimMeasurements.toMutableList(),
                suspectedUser.id!!,
                suspectMeasurements.toMutableList(),
                alert.creationInstant
            )
        }
    }
}