package net.ddns.jaronsky.debter.security.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 * Created by Wojciech Jaronski
 *
 */
//@Configuration
//@Component
@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
data class JwtProperties (
        val header: String,
        val secret: String,
        val expiration: String,
        val route: Route

)


