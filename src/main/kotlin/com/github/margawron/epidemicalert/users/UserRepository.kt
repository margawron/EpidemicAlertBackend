package com.github.margawron.epidemicalert.users

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByUserName(username: String): User?

    fun findByUserEmail(mail: String): User?

}