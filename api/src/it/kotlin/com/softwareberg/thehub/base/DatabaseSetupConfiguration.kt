package com.softwareberg.thehub.base

import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DatabaseSetupConfiguration {

    @Bean
    fun databaseSetup(dataSource: DataSource): DatabaseSetup {
        val db = DSL.using(dataSource, SQLDialect.POSTGRES_10)
        return DatabaseSetup(db)
    }
}
