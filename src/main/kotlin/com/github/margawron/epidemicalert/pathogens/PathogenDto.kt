package com.github.margawron.epidemicalert.pathogens


data class PathogenDto (
    var id: Long?,
    var name: String,
    var contagiousPeriod: Int,
    var contagiousPeriodResolution: ContagiousPeriodResolution,
){
    companion object{
        fun fromEntity(pathogen: Pathogen) = PathogenDto(
            pathogen.id!!,
            pathogen.name,
            pathogen.contagiousPeriod,
            pathogen.contagiousPeriodResolution
        )
    }
}