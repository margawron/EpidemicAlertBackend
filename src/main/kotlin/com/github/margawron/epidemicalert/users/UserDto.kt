package com.github.margawron.epidemicalert.users

import java.time.Instant

class UserDto(
    var id: Long?,
    var username: String,
    var useremail: String,
    var role: Role,
    var accountCreationDate: Instant?,
    var accountExpirationDate: Instant?,
    var accountState: AccountState
) {
    companion object {
        fun fromEntity(user: User) = UserDto(
            user.id,
            user.userName,
            user.userEmail,
            user.role,
            user.accountCreationDate,
            user.accountExpirationDate,
            user.accountState
        )
    }
}