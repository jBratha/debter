package net.ddns.jaronsky.debter.security.config

import lombok.extern.slf4j.Slf4j
import net.ddns.jaronsky.debter.security.properties.JwtProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.hibernate.bytecode.BytecodeLogger.LOGGER
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.core.env.*
import java.util.stream.StreamSupport


@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class SecurityConfig {
    val logger = logger()

//    @Bean
    fun securityConfigRunner(): String {
        logger.info("securityConfigRunner()")
        return "securityConfigRunner"
    }

//    @EventListener
    fun handleContextRefreshed(event: ContextRefreshedEvent) {
        printActiveProperties(event.applicationContext.environment as ConfigurableEnvironment)
    }

    fun printActiveProperties(env: ConfigurableEnvironment) {
        println("************************* ACTIVE APP PROPERTIES ******************************")
        env.propertySources
                .filter { it.name.contains("applicationConfig") }
                .map { it as EnumerablePropertySource<*> }
                .map { it -> it.propertyNames.toList() }
                .flatMap { it }
                .distinctBy { it }
                .sortedBy { it }
                .forEach { it ->
                    try {
                        println("$it=${env.getProperty(it)}")
                    } catch (e: Exception) {
                        logger.warn("$it -> ${e.message}")
                    }
                }
        println("******************************************************************************")
    }
}
