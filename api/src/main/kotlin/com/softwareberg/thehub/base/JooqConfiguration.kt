package com.softwareberg.thehub.base

import org.jooq.SQLDialect
import org.jooq.impl.DefaultConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class JooqConfiguration {

    @Bean
    fun jooqDatabaseConfiguration(dataSource: DataSource): org.jooq.Configuration {
        val configuration = DefaultConfiguration()
        configuration.set(dataSource)
        configuration.set(SQLDialect.POSTGRES_10)
        return configuration
    }
}
