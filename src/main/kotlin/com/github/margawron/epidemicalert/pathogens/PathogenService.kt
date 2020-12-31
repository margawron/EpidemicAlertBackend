package com.github.margawron.epidemicalert.pathogens

import com.github.margawron.epidemicalert.exceptions.ErrorCodeException
import org.springframework.stereotype.Service

@Service
class PathogenService(
    private val pathogenRepository: PathogenRepository
) {

    fun getPathogenContainingStringInName(string: String): List<Pathogen> = pathogenRepository.findByNameContainingIgnoreCase(string)

    fun getPathogenById(pathogenId: Long): Pathogen = pathogenRepository.findById(pathogenId).orElse(null) ?: throw ErrorCodeException(this::class, "pathogen.given_id_does_not_exist")

    fun getAllPathogens(): List<Pathogen> = pathogenRepository.findAll()

    fun createPathogenWithGivenName(pathogen: Pathogen): Pathogen {
        val existingPathogen = pathogenRepository.findByName(pathogen.name)
        if(existingPathogen != null){
            throw ErrorCodeException(Pathogen::class, "pathogen.given_name_is_used")
        }
        return pathogenRepository.save(pathogen)
    }
}