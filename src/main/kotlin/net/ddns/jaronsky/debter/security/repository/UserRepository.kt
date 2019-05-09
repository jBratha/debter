package net.ddns.jaronsky.debter.security.repository

import net.ddns.jaronsky.debter.model.security.User
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by Wojciech Jaronski
 *
 */

interface UserRepository: JpaRepository<User, Long> {
    public fun findByUsername(username: String): User?
}