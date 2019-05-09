package net.ddns.jaronsky.debter.security

import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import io.jsonwebtoken.ExpiredJwtException
import net.ddns.jaronsky.debter.security.properties.JwtProperties
import org.springframework.beans.factory.annotation.Qualifier
import javax.servlet.ServletException
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.*
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.userdetails.UserDetails




/**
 * Created by Wojciech Jaronski
 *
 */

@Component
class JwtAuthorizationTokenFilter(
        private val jwtProperties: JwtProperties,
        @Qualifier("jwtUserDetailsService")
        private val userDetailsService: UserDetailsService,
        private val jwtTokenUtil: JwtTokenUtil
): OncePerRequestFilter() {

//    @Throws(IOException::class, ServletException::class)
//    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain) {
//        val header = req.getHeader(jwtProperties.header)
//        var username: String? = null
//        var authToken: String? = null
//        if (header != null && header.startsWith("Bearer ")) {
//            authToken = header.replace("Bearer ", "")
//            try {
//                username = jwtTokenUtil!!.getUsernameFromToken(authToken)
//            } catch (e: IllegalArgumentException) {
//                logger.error("an error occured during getting username from token", e)
//            } catch (e: ExpiredJwtException) {
//                logger.warn("the token is expired and not valid anymore", e)
//            } catch (e: SignatureException) {
//                logger.error("Authentication Failed. Username or Password not valid.")
//            }
//
//        } else {
//            logger.warn("couldn't find bearer string, will ignore the header")
//        }
//        if (username != null && SecurityContextHolder.getContext().authentication == null) {
//
//            val userDetails = userDetailsService!!.loadUserByUsername(username)
//
//            if (jwtTokenUtil!!.validateToken(authToken, userDetails)) {
//                val authentication = UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(SimpleGrantedAuthority("ROLE_ADMIN")))
//                authentication.details = WebAuthenticationDetailsSource().buildDetails(req)
//                logger.info("authenticated user $username, setting security context")
//                SecurityContextHolder.getContext().authentication = authentication
//            }
//        }
//
//        chain.doFilter(req, res)
//    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        logger.debug("processing authentication for '${request.requestURL}'" )

        val requestHeader = request.getHeader(jwtProperties.header)

        var username: String? = null
        var authToken: String? = null
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7)
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken)
            } catch (e: IllegalArgumentException) {
                logger.error("an error occurred during getting username from token", e)
            } catch (e: ExpiredJwtException) {
                logger.warn("the token is expired and not valid anymore", e)
            }

        } else {
            logger.warn("couldn't find bearer string, will ignore the header")
        }

        logger.debug("checking authentication for user '${username}'")
        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            logger.debug("security context was null, so authorizing user")

            // It is not compelling necessary to load the use details from the database. You could also store the information
            // in the token and read it from it. It's up to you ;)
            val userDetails: UserDetails
            try {
                userDetails = userDetailsService.loadUserByUsername(username)
            } catch (e: UsernameNotFoundException) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.message)
                return
            }


            // For simple validation it is completely sufficient to just check the token integrity. You don't have to call
            // the database compellingly. Again it's up to you ;)
            if (jwtTokenUtil.validateToken(authToken!!, userDetails)) {
                val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                logger.info("authorized user '${username}', setting security context")
                SecurityContextHolder.getContext().authentication = authentication
            }
        }

        chain.doFilter(request, response)
    }

}