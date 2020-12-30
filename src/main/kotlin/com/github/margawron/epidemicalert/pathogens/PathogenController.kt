package com.github.margawron.epidemicalert.pathogens

import com.github.margawron.epidemicalert.exceptions.ErrorCodeException
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
class PathogenController(
    private val pathogenService: PathogenService
) {

    @GetMapping("pathogen/id/{pathogenId}")
    @PreAuthorize("@permissionEvaluator.isRegistered(authentication)")
    fun getPathogen(@PathVariable("pathogenId") pathogenId: Long): ResponseEntity<Pathogen> {
        val pathogen = pathogenService.getPathogenById(pathogenId) ?: throw ErrorCodeException(this::class, "pathogen.given_id_does_not_exist")
        return ResponseEntity.ok(pathogen)
    }

    @GetMapping("pathogen/all")
    @PreAuthorize("@permissionEvaluator.isAtLeastModerator(authentication)")
    fun getAllPathogens(): ResponseEntity<List<Pathogen>> {
        val pathogens = pathogenService.getAllPathogens()
        return ResponseEntity.ok(pathogens)
    }

    @GetMapping("pathogen/name/{name}")
    @PreAuthorize("@permissionEvaluator.isAtLeastModerator(authentication)")
    fun findPathogensContainingStringInName(@PathVariable("name") string: String): ResponseEntity<List<Pathogen>>{
        val pathogens = pathogenService.getPathogenContainingStringInName(string)
        return ResponseEntity.ok(pathogens)
    }

    @PostMapping("pathogen")
    @PreAuthorize("@permissionEvaluator.isAdmin(authentication)")
    fun createPathogen(@RequestBody pathogen: Pathogen): ResponseEntity<Pathogen>{
        val createdPathogen = pathogenService.createPathogenWithGivenName(pathogen)
        return ResponseEntity.ok(createdPathogen)
    }
}