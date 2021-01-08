package com.github.margawron.epidemicalert.alerts

import com.github.margawron.epidemicalert.exceptions.ErrorCodeException
import com.github.margawron.epidemicalert.measurements.Measurement
import com.github.margawron.epidemicalert.notifications.PushService
import com.github.margawron.epidemicalert.proximity.ProximityMeasurement
import com.github.margawron.epidemicalert.proximity.ProximityType
import com.github.margawron.epidemicalert.suspects.Suspect
import com.github.margawron.epidemicalert.users.User
import org.springframework.stereotype.Service

@Service
class AlertService(
    private val alertRepository: AlertRepository,
    private val pushService: PushService,
) {

    fun getAlertById(alertId: Long): Alert = alertRepository.findById(alertId).orElse(null) ?: throw ErrorCodeException(this::class, "alert.given_id_does_not_exist")


    fun createAlert(
        victim: User,
        suspect: Suspect,
        victimSetOfProximities: MutableSet<Measurement>,
        suspectSetOfProximities: MutableSet<Measurement>
    ): Alert {
        val alert = Alert(
            null,
            suspect,
            victim
        )
        val victimProximities = victimSetOfProximities.map {
            ProximityMeasurement(
                null,
                it,
                alert,
                ProximityType.VICTIM
            )
        }.toSet()
        val suspectProximities = suspectSetOfProximities.map {
            ProximityMeasurement(
                null,
                it,
                alert,
                ProximityType.SUSPECT
            )
        }.toSet()
        alert.proximityMeasurements.addAll(victimProximities)
        alert.proximityMeasurements.addAll(suspectProximities)
        val savedAlert = alertRepository.save(alert)
        pushService.sendNotification(savedAlert)
        return savedAlert
    }
}