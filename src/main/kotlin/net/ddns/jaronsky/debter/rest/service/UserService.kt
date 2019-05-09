package net.ddns.jaronsky.debter.rest.service

import net.ddns.jaronsky.debter.model.UserDTO
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

    @PreAuthorize("hasRole('ADMIN')")
    fun findAll(): List<UserDTO> {
        return jwtUserDetailsService.fetchUsers();
    }

    @Throws(UsernameNotFoundException::class)
    fun getAuthorities(username: String): List<AuthorityName?> {
        return jwtUserDetailsService.getAuthorities(username);
    }

    @PreAuthorize("#username == authentication.principal.username or hasRole('ADMIN')")
    fun findUserByName(username: String): JwtUser {
        val jwtUser = jwtUserDetailsService.loadUserByUsername(username)
        return jwtUser as JwtUser
    }

    fun infoAboutYourself(): JwtUser {
        return jwtUserDetailsService.loadUserByUsername(SecurityContextHolder.getContext().authentication.name) as JwtUser
    }

    fun registerUser(user: RegisterUser) {
        jwtUserDetailsService.registerUser(
                User.newUser(
                        user,
                        passwordEncoder.encode(user.password),
                        arrayListOf(Authority(name = AuthorityName.ROLE_USER))));
    }
}