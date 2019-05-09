package net.ddns.jaronsky.debter.security.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Created by Wojciech Jaronski
 *
 */
//@Configuration
//@Component
@ConfigurationProperties(prefix = "jwt")
class JwtProperties(
        val header: String,
        val route: Route,
        val expiration: String,
        val secret: String
)

class Route(
        val authentication: Authentication
)

class Authentication(
        val path: String,
        val refresh: String
)
