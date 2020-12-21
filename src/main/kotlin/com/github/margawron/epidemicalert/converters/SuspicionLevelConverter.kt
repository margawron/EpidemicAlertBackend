package com.github.margawron.epidemicalert.converters

import com.github.margawron.epidemicalert.suspects.SuspicionLevel
import javax.persistence.AttributeConverter

class SuspicionLevelConverter: AttributeConverter<SuspicionLevel, String> {

    override fun convertToDatabaseColumn(attribute: SuspicionLevel): String = attribute.mapping

    override fun convertToEntityAttribute(columnData: String): SuspicionLevel =
        SuspicionLevel.fromDatabaseMapping(columnData)
}