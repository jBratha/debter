package net.ddns.jaronsky.debter.security.controller

import net.ddns.jaronsky.debter.security.JwtTokenUtil
import net.ddns.jaronsky.debter.security.JwtUser
import net.ddns.jaronsky.debter.security.properties.JwtProperties
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class UserRestController (
        val jwtTokenUtil: JwtTokenUtil? = null,
        @Qualifier("jwtUserDetailsService")
        val userDetailsService: UserDetailsService? = null,
        private val jwtProperties: JwtProperties

) {

    @GetMapping("user")
    fun getAuthenticatedUser(request: HttpServletRequest): JwtUser {
        val token = request.getHeader(jwtProperties.header).substring(7)
        val username = jwtTokenUtil!!.getUsernameFromToken(token)
        return userDetailsService!!.loadUserByUsername(username) as JwtUser
    }

}