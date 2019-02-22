package com.softwareberg.thehub.base

import javax.sql.DataSource
import org.jooq.SQLDialect
import org.jooq.impl.DefaultConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

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
