package com.github.margawron.epidemicalert.converters

import com.github.margawron.epidemicalert.proximity.ProximityType
import javax.persistence.AttributeConverter

class ProximityTypeConverter : AttributeConverter<ProximityType, String> {

    override fun convertToDatabaseColumn(attribute: ProximityType): String = attribute.mapping

    override fun convertToEntityAttribute(columnData: String): ProximityType =
        ProximityType.fromDatabaseMapping(columnData)

}