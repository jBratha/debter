package net.ddns.jaronsky.debter.config

import lombok.extern.slf4j.Slf4j
import org.apache.catalina.Context
import org.apache.catalina.connector.Connector
import org.apache.tomcat.util.descriptor.web.SecurityCollection
import org.apache.tomcat.util.descriptor.web.SecurityConstraint
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.server.ServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Slf4j
@Configuration
@Profile("ssl")
class HttpsPortTomcat {

    @Value("\${server.port}")
    internal var httpsPort: Int = 8085
    @Value("\${http.port}")
    internal var httpPort: Int = 0

    @Bean
    fun servletContainer(): ServletWebServerFactory {
//        log.info("Http: {}", httpPort)
//        log.info("Https: {}", httpsPort)
        val tomcat = object : TomcatServletWebServerFactory() {
            override fun postProcessContext(context: Context) {
                val securityConstraint = SecurityConstraint()
                securityConstraint.userConstraint = "365295"
                val collection = SecurityCollection()
                collection.addPattern("/*")
                securityConstraint.addCollection(collection)
                context.addConstraint(securityConstraint)
            }
        }
        tomcat.addAdditionalTomcatConnectors(redirectConnector())
        return tomcat
    }

    private fun redirectConnector(): Connector {

        val connector = Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL)
        connector.scheme = "http"
        connector.port = httpPort
        connector.secure = false
        connector.redirectPort = httpsPort
        return connector
    }
}