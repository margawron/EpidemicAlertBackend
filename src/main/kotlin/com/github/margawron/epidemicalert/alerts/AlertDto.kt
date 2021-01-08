package com.github.margawron.epidemicalert.alerts

import com.github.margawron.epidemicalert.exceptions.ErrorCodeException
import com.github.margawron.epidemicalert.proximity.ProximityMeasurementDto
import com.github.margawron.epidemicalert.proximity.ProximityType
import com.github.margawron.epidemicalert.users.User
import kotlin.jvm.Throws

data class AlertDto(
    var id: Long,
    var proximityType: ProximityType,
    var victimMeasurements: MutableList<ProximityMeasurementDto>,
    var suspectMeasurements: MutableList<ProximityMeasurementDto>,
){
    companion object{

        @Throws(ErrorCodeException::class)
        fun fromEntityForUser(alert: Alert, user: User) : AlertDto {
            val id = alert.id!!
            val proximityType: ProximityType = when {
                alert.victim.id == user.id -> {
                    ProximityType.VICTIM
                }
                alert.suspect.suspect.id == user.id -> {
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
                victimMeasurements.toMutableList(),
                suspectMeasurements.toMutableList(),
            )
        }
    }
}