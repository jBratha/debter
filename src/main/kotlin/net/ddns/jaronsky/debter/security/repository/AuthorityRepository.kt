package net.ddns.jaronsky.debter.security.repository

import net.ddns.jaronsky.debter.model.security.Authority
import net.ddns.jaronsky.debter.model.security.AuthorityName
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by Wojciech Jaronski
 *
 */

interface AuthorityRepository: JpaRepository<Authority, Long> {
    public fun findByName(name: AuthorityName): Authority?
}