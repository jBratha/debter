package net.ddns.jaronsky.debter.security

import net.ddns.jaronsky.debter.model.security.Authority
import net.ddns.jaronsky.debter.model.security.User
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority

/**
 * Created by Wojciech Jaronski
 *
 */

class JwtUserFactory() {
    companion object {
        fun create(user: User): JwtUser {
            return JwtUser(
                    id = user.id,
                    username = user.username!!,
                    firstname = user.firstname!!,
                    lastname = user.lastname!!,
                    email = user.email!!,
                    password = user.password!!,
                    authorities = mapToGrantedAuthorities(user.authorities!!),
                    enabled = user.enabled!!,
                    lastPasswordResetDate = user.lastPasswordResetDate!!
            )
        }
        fun mapToGrantedAuthorities(authorities: List<Authority>): List<GrantedAuthority> {
            return authorities.map {
                it -> SimpleGrantedAuthority(it.name!!.name)
            }
        }
    }
}