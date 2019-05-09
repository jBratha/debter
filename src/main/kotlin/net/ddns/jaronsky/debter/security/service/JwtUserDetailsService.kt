package net.ddns.jaronsky.debter.security.service

import lombok.extern.slf4j.Slf4j
import net.ddns.jaronsky.debter.model.UserDTO
import net.ddns.jaronsky.debter.model.security.Authority
import net.ddns.jaronsky.debter.model.security.AuthorityName
import net.ddns.jaronsky.debter.model.security.User
import net.ddns.jaronsky.debter.security.JwtUserFactory
import net.ddns.jaronsky.debter.security.repository.UserRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import kotlin.math.log


/**
 * Created by Wojciech Jaronski
 *
 */
@Slf4j
@Service("jwtUserDetailsService")
class JwtUserDetailsService (
        private val userRepository: UserRepository
) : UserDetailsService{

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)

        return if (user == null) {
            throw UsernameNotFoundException(String.format("No user found with username '%s'.", username))
        } else {
            JwtUserFactory.create(user)
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    fun fetchUsers(): List<UserDTO> {
        return userRepository.findAll().filter { user -> user.authorities?.map { auth -> auth.name }!!.contains(AuthorityName.ROLE_USER) && user.enabled!! } .map { user -> UserDTO(user.username!!) }
    }
}
