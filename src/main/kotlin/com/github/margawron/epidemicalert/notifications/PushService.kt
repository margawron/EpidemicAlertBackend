package com.github.margawron.epidemicalert.notifications

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class PushService {

    val log = LoggerFactory.getLogger(PushService::class.java)

    fun sendNotification(){
        val notification = Notification("Test", "test")
        val message = Message.builder()
            .setToken("ekyaSMonS-uC5JHI0zCIyx:APA91bEbCJJGOydIruzORPIQ_--8SGgMBQYclIpuAf7DOPDM1Uf2laZoVUsjfqLcqqoU71z2LwiXAW947CQBLVUwF9gW1E7SjewL2QV9f71tk0gn-IZFURLlUI_C_gz5heLVjGT9B1ui")
            .setNotification(notification)
            .build()
        val sentMessage = FirebaseMessaging.getInstance().send(message)
        log.debug("OE", "Push notification sent")
    }
}

