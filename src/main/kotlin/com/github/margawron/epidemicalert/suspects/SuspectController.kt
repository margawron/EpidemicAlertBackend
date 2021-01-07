package com.github.margawron.epidemicalert.suspects

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SuspectController(
    private val suspectService: SuspectService
){

    @PostMapping("suspect")
    @PreAuthorize("@permissionEvaluator.isModerator(authentication)")
    fun reportSuspectedUser(@RequestBody suspectDto: SuspectDto): ResponseEntity<Any> {
        suspectService.createAndRunAlertingTask(suspectDto)
        return ResponseEntity.ok(suspectDto)
    }
}