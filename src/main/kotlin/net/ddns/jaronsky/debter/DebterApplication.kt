package net.ddns.jaronsky.debter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import

//@EnableConfigurationProperties
@SpringBootApplication
//@ComponentScan("net.ddns.jaronsky.debter.config")
class DebterApplication

fun main(args: Array<String>) {
    runApplication<DebterApplication>(*args)
}
