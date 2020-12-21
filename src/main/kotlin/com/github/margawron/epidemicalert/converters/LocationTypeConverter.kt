package com.github.margawron.epidemicalert.converters

import com.github.margawron.epidemicalert.location.LocationType
import javax.persistence.AttributeConverter

class LocationTypeConverter: AttributeConverter<LocationType, String> {

    override fun convertToDatabaseColumn(attribute: LocationType): String = attribute.mapping

    override fun convertToEntityAttribute(columnData: String): LocationType =
        LocationType.fromDatabaseMapping(columnData)
}