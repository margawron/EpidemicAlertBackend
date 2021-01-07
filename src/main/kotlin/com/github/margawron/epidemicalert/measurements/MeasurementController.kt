package com.github.margawron.epidemicalert.measurements

import com.github.margawron.epidemicalert.common.IdMapping
import com.github.margawron.epidemicalert.users.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class MeasurementController(
    private val userService: UserService,
    private val measurementService: MeasurementService
) {

    @PostMapping("measurement")
    @PreAuthorize("@permissionEvaluator.isRegistered(authentication)")
    fun saveUserMeasurements(
        auth: Authentication,
        @RequestParam(name = "deviceId") deviceId: Long, @RequestBody @Valid measurementsDto: List<MeasurementDto>
    ): ResponseEntity<List<IdMapping>> {
        val user = userService.userFromAuth(auth)
        val idMappings = measurementService.saveMeasurementsAndGetIdMappings(user, deviceId, measurementsDto)
        return ResponseEntity.ok(idMappings)
    }
}