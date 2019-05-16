package net.ddns.jaronsky.debter.config

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

@Component
@Profile("h2")
class DbInitConfig(
        private val dataSource: DataSource
) {

    @Bean
    fun runOnce() {
        ScriptUtils.executeSqlScript(dataSource.connection, EncodedResource(ClassPathResource("data/data-h2.sql")))
    }

}