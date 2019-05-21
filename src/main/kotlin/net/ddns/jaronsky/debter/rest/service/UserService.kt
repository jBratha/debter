package net.ddns.jaronsky.debter.rest.service

import net.ddns.jaronsky.debter.model.security.Authority
import net.ddns.jaronsky.debter.model.security.AuthorityName
import net.ddns.jaronsky.debter.model.security.User
import net.ddns.jaronsky.debter.rest.model.RegisterUser
import net.ddns.jaronsky.debter.security.JwtUser
import net.ddns.jaronsky.debter.security.service.JwtUserDetailsService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


/**
 * Created by Wojciech Jaronski
 *
 */

@Service
class UserService(
        private val jwtUserDetailsService: JwtUserDetailsService,
        private val passwordEncoder: PasswordEncoder
) {

    companion object : Log()

    @PreAuthorize("hasRole('ADMIN')")
    fun findAll(): List<User> {
        return jwtUserDetailsService.getAll()
    }

    @Throws(UsernameNotFoundException::class)
    fun getAuthorities(username: String): List<AuthorityName?> {
        return jwtUserDetailsService.getAuthorities(username)
    }

    @PreAuthorize("#username == authentication.principal.username or hasRole('ADMIN')")
    fun findJwtUserByName(username: String): JwtUser {
        val jwtUser = jwtUserDetailsService.loadUserByUsername(username)
        return jwtUser as JwtUser
    }

    fun infoAboutYourself(): User {
        return jwtUserDetailsService.getUserByUsername(SecurityContextHolder.getContext().authentication.name)
    }

    fun registerUser(user: RegisterUser) {
        jwtUserDetailsService.registerUser(
                User.newUser(
                        user,
                        passwordEncoder.encode(user.password),
                        arrayListOf(Authority(name = AuthorityName.ROLE_USER))))
    }

    fun findUserByName(name: String): User {
        return jwtUserDetailsService.getUserByUsername(name)
    }

    fun findEnabledUsers(): List<String> {
        return jwtUserDetailsService.getEnabledUsers().map { u -> u.username!! }
    }
}