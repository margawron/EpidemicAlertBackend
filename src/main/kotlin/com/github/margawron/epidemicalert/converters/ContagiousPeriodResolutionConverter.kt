package com.github.margawron.epidemicalert.converters

import com.github.margawron.epidemicalert.pathogens.ContagiousPeriodResolution
import javax.persistence.AttributeConverter

class ContagiousPeriodResolutionConverter : AttributeConverter<ContagiousPeriodResolution, String> {

    override fun convertToDatabaseColumn(attribute: ContagiousPeriodResolution): String = attribute.mapping

    override fun convertToEntityAttribute(columnData: String): ContagiousPeriodResolution =
        ContagiousPeriodResolution.fromDatabaseMapping(columnData)

}