package net.ddns.jaronsky.debter.rest.service

import net.ddns.jaronsky.debter.model.UserDTO
import net.ddns.jaronsky.debter.security.JwtUser
import net.ddns.jaronsky.debter.security.service.JwtUserDetailsService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.security.core.context.SecurityContextHolder



/**
 * Created by Wojciech Jaronski
 *
 */

@Service
class UserService(
        private val jwtUserDetailsService: JwtUserDetailsService
) {

    @PreAuthorize("hasRole('ADMIN')")
    fun findAll(): List<UserDTO> {
        return jwtUserDetailsService.fetchUsers();
    }

    @PreAuthorize("#username == authentication.principal.username or hasRole('ADMIN')")
    fun findUserByName(username: String): JwtUser {
        val jwtUser = jwtUserDetailsService.loadUserByUsername(username)
        return jwtUser as JwtUser
    }

    fun infoAboutYourself(): JwtUser {
        return jwtUserDetailsService.loadUserByUsername (SecurityContextHolder.getContext().authentication.name) as JwtUser
    }
}