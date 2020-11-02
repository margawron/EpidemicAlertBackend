package com.github.margawron.epidemicalert.users

import org.springframework.data.repository.PagingAndSortingRepository

interface UserRepository : PagingAndSortingRepository<User, Long> {

    fun findByUserName(username: String): User?

    fun findByUserEmail(mail: String): User?

}