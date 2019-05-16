package net.ddns.jaronsky.debter.security.controller

import net.ddns.jaronsky.debter.rest.service.UserService
import net.ddns.jaronsky.debter.security.JwtAuthenticationRequest
import net.ddns.jaronsky.debter.security.JwtTokenUtil
import net.ddns.jaronsky.debter.security.JwtUser
import net.ddns.jaronsky.debter.security.properties.JwtProperties
import net.ddns.jaronsky.debter.security.service.JwtAuthenticationResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
class AuthenticationRestController(
        private val authenticationManager: AuthenticationManager? = null,
        private val jwtTokenUtil: JwtTokenUtil? = null,
        private val jwtProperties: JwtProperties,
        @Qualifier("jwtUserDetailsService")
        private val userDetailsService: UserDetailsService? = null,
        private val userService: UserService

) {

    @PostMapping(value = ["\${jwt.route.authentication.path}"])
    @Throws(AuthenticationException::class)
    fun createAuthenticationToken(@RequestBody authenticationRequest: JwtAuthenticationRequest): ResponseEntity<*> {

        authenticate(authenticationRequest.username, authenticationRequest.password)

        // Reload password post-security so we can generate the token
        val userDetails = userDetailsService!!.loadUserByUsername(authenticationRequest.username)
        val token = jwtTokenUtil!!.generateToken(userDetails)
        val roles = userService.getAuthorities(authenticationRequest.username)
        val username = authenticationRequest.username

        // Return the token
        return ResponseEntity.ok<Any>(JwtAuthenticationResponse(username, token, roles))
    }

    @GetMapping(value = ["\${jwt.route.authentication.refresh}"])
    fun refreshAndGetAuthenticationToken(request: HttpServletRequest): ResponseEntity<*> {
        val authToken = request.getHeader(jwtProperties.header)
        val token = authToken.substring(7)
        val username = jwtTokenUtil!!.getUsernameFromToken(token)
        val user = userDetailsService!!.loadUserByUsername(username) as JwtUser

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            val refreshedToken = jwtTokenUtil.refreshToken(token)
            return ResponseEntity.ok<Any>(JwtAuthenticationResponse(token = refreshedToken))
        } else {
            return ResponseEntity.badRequest().body<Any>(null)
        }
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(e: AuthenticationException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.message)
    }

    /**
     * Authenticates the user. If something is wrong, an [AuthenticationException] will be thrown
     */
    private fun authenticate(username: String, password: String) {
        Objects.requireNonNull(username)
        Objects.requireNonNull(password)

        try {
            authenticationManager!!.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
            throw AuthenticationException("User is disabled!", e)
        } catch (e: BadCredentialsException) {
            throw AuthenticationException("Bad credentials!", e)
        }

    }
}