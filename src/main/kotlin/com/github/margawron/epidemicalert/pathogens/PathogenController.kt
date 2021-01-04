package com.github.margawron.epidemicalert.pathogens

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
class PathogenController(
    private val pathogenService: PathogenService
) {

    @GetMapping("pathogen/id/{pathogenId}")
    @PreAuthorize("@permissionEvaluator.isRegistered(authentication)")
    fun getPathogen(@PathVariable("pathogenId") pathogenId: Long): PathogenDto {
        val pathogen = pathogenService.getPathogenById(pathogenId)
        return PathogenDto.fromEntity(pathogen)
    }

    @GetMapping("pathogen/all")
    @PreAuthorize("@permissionEvaluator.isAtLeastModerator(authentication)")
    fun getAllPathogens(): List<PathogenDto> {
        val pathogens = pathogenService.getAllPathogens()
        return pathogens.map { PathogenDto.fromEntity(it) }
    }

    @GetMapping("pathogen/name/{name}")
    @PreAuthorize("@permissionEvaluator.isAtLeastModerator(authentication)")
    fun findPathogensContainingStringInName(@PathVariable("name") string: String): List<PathogenDto>{
        val pathogens = pathogenService.getPathogenContainingStringInName(string)
        return pathogens.map { PathogenDto.fromEntity(it) }
    }

    @PostMapping("pathogen")
    @PreAuthorize("@permissionEvaluator.isAdmin(authentication)")
    fun createPathogen(@RequestBody pathogen: Pathogen): Pathogen {
        return pathogenService.createPathogenWithGivenName(pathogen)
    }
}