package net.ddns.jaronsky.debter.security

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*


/**
 * Created by Wojciech Jaronski
 *
 */

class JwtUser(
        @JsonIgnore private val id: Long?,
        private val username: String,
        private val firstname: String,
        private val lastname: String,
        @JsonIgnore private val password: String,
        private val email: String,
        private val authorities: Collection<GrantedAuthority>,
        private val enabled: Boolean,
        private val lastPasswordResetDate: Date
): UserDetails {

    override fun getUsername(): String {
        return username
    }

    @JsonIgnore
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isAccountNonLocked(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String {
        return password
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun isEnabled(): Boolean {
        return enabled
    }

    @JsonIgnore
    fun getLastPasswordResetDate(): Date {
        return lastPasswordResetDate
    }
}