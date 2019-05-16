package net.ddns.jaronsky.debter.security.service

import lombok.extern.slf4j.Slf4j
import net.ddns.jaronsky.debter.model.UserDTO
import net.ddns.jaronsky.debter.model.security.AuthorityName
import net.ddns.jaronsky.debter.model.security.User
import net.ddns.jaronsky.debter.security.JwtUserFactory
import net.ddns.jaronsky.debter.security.repository.AuthorityRepository
import net.ddns.jaronsky.debter.security.repository.UserRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


/**
 * Created by Wojciech Jaronski
 *
 */
@Slf4j
@Service("jwtUserDetailsService")
class JwtUserDetailsService(
        private val userRepository: UserRepository,
        private val authorityRepository: AuthorityRepository
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)

        return if (user == null) {
            throw UsernameNotFoundException(String.format("No user found with username '%s'.", username))
        } else {
            JwtUserFactory.create(user)
        }
    }

    @Throws(UsernameNotFoundException::class)
    fun getAuthorities(username: String): List<AuthorityName?> {
        return userRepository.findByUsername(username)?.authorities!!.map { authority -> authority.name }
    }

    @PreAuthorize("hasRole('ADMIN')")
    fun fetchUsers(): List<UserDTO> {
        return userRepository.findAll().filter { user -> user.authorities?.map { auth -> auth.name }!!.contains(AuthorityName.ROLE_USER) && user.enabled!! }.map { user -> UserDTO(user.username!!) }
    }

    @PreAuthorize("hasRole('ADMIN')")
    fun getAll(): List<User> {
        return userRepository.findAll()
    }

    fun registerUser(user: User) {
        val roles = authorityRepository.findAll()
        user.authorities = user.authorities?.map { it -> roles.first { role -> role.name == it.name } }
        userRepository.save(user)
    }
}
