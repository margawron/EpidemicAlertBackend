package com.github.margawron.epidemicalert.notifications

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.margawron.epidemicalert.alerts.Alert
import com.github.margawron.epidemicalert.alerts.AlertDto
import com.github.margawron.epidemicalert.proximity.ProximityType
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class PushService(
    val objectMapper: ObjectMapper,
) {

    val log = LoggerFactory.getLogger(PushService::class.java)

    fun sendNotification(alert: Alert){

        val victimAlertBody = AlertDto(
            alert.id!!,
            ProximityType.VICTIM
        )
        val notification = Notification("Alert", objectMapper.writeValueAsString(victimAlertBody))
        val victimDevices = alert.victim.userDevices
        for(device in victimDevices){
            try {
                val message = Message.builder()
                    .setToken(device.firebaseToken)
                    .setNotification(notification)
                    .build()
                log.debug("OE", "Pre push notification sent")
                FirebaseMessaging.getInstance().send(message)
                log.debug("OE", "Post push notification sent")
            } catch (e: Exception){
                log.error(e.message)
            }
        }
    }
}

