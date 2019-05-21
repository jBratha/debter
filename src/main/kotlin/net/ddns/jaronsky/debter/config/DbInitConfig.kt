package net.ddns.jaronsky.debter.config

import lombok.extern.slf4j.Slf4j
import net.ddns.jaronsky.debter.rest.service.Log
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.support.EncodedResource
import org.springframework.jdbc.datasource.init.ScriptUtils
import org.springframework.stereotype.Component
import javax.sql.DataSource

/**
 * Created by Wojciech Jaronski
 *
 */

@Slf4j
@Component
@Profile("init")
class DbInitConfig(
        private val dataSource: DataSource
) {
    companion object : Log()

    @Bean
    @Profile("h2")
    fun initWhenH2() {
        val script = "data/data-h2.sql"
        logger.info("Initializing H2 DB with script\'$script\'")
        ScriptUtils.executeSqlScript(dataSource.connection, EncodedResource(ClassPathResource(script)))
    }

    @Bean
    @Profile("dev && mysql")
    fun initWhenMysql() {
        val script = "data/data-mysql.sql"
        logger.info("Initializing Mysql DB with script\'$script\'")
        ScriptUtils.executeSqlScript(dataSource.connection, EncodedResource(ClassPathResource(script)))
    }

    @Bean
    @Profile("prod && mysql")
    fun initWhenMysql2() {
        val script = "data/data-mysql-prod.sql"
        logger.info("Initializing Mysql DB with script\'$script\'")
        ScriptUtils.executeSqlScript(dataSource.connection, EncodedResource(ClassPathResource(script)))
    }
}