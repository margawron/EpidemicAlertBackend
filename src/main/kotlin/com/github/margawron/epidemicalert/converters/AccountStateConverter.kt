package com.github.margawron.epidemicalert.converters

import com.github.margawron.epidemicalert.users.AccountState
import javax.persistence.AttributeConverter

class AccountStateConverter : AttributeConverter<AccountState, String> {

    override fun convertToDatabaseColumn(attribute: AccountState): String = attribute.mapping

    override fun convertToEntityAttribute(columnData: String): AccountState =
            AccountState.fromDatabaseMapping(columnData)

}