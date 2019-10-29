package net.ddns.jaronsky.debter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
class DebterApplication

fun main(args: Array<String>) {
    runApplication<DebterApplication>(*args)
}
