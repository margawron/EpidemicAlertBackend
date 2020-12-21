package com.github.margawron.epidemicalert.converters

import com.github.margawron.epidemicalert.users.Role
import javax.persistence.AttributeConverter

class RoleConverter : AttributeConverter<Role, String> {

    override fun convertToDatabaseColumn(attribute: Role): String = attribute.authority

    override fun convertToEntityAttribute(columnData: String): Role = Role.fromDatabaseMapping(columnData)

}