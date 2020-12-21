package com.github.margawron.epidemicalert.converters

import com.github.margawron.epidemicalert.zones.ZoneEmergencyLevel
import javax.persistence.AttributeConverter

class ZoneEmergencyLevelConverter : AttributeConverter<ZoneEmergencyLevel, String> {

    override fun convertToDatabaseColumn(attribute: ZoneEmergencyLevel): String = attribute.mapping

    override fun convertToEntityAttribute(columnData: String): ZoneEmergencyLevel = ZoneEmergencyLevel.fromDatabaseMapping(columnData)
}