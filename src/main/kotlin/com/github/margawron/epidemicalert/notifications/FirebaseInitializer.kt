package com.github.margawron.epidemicalert.notifications

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.IOException
import javax.annotation.PostConstruct

@Service
class FirebaseInitializer(
    @Value(value = "\${app.firebase-configuration-file}")
    private val configPath: String
) {

    companion object{
        val log = LoggerFactory.getLogger(FirebaseInitializer::class.java)
    }

    @PostConstruct
    fun init() {
        try {
            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(ClassPathResource(configPath).inputStream))
                .build()
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options)
                log.info("Firebase app init")
            }
        } catch (e: IOException){
            log.error(e.message)
        }
    }
}
