package com.softwareberg.base

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.junit.rules.ExternalResource

class DatabaseResource : ExternalResource() {

    private lateinit var dataSource: HikariDataSource
    private lateinit var db: DSLContext

    override fun before() {
        super.before()
        dataSource = createLocalDataSource()
        db = DSL.using(dataSource, SQLDialect.POSTGRES_10)
        cleanAndMigrateDatabase()
    }

    override fun after() {
        super.after()
        dataSource.close()
    }

    private fun createLocalDataSource(): HikariDataSource {
        val config = ConfigFactory.load()
        val hikariConfig = HikariConfig()
        hikariConfig.jdbcUrl = config.getString("datasource.jdbcUrl")
        hikariConfig.username = config.getString("datasource.username")
        hikariConfig.password = config.getString("datasource.password")
        return HikariDataSource(hikariConfig)
    }

    private fun cleanAndMigrateDatabase() {
        val flyway = Flyway()
        flyway.dataSource = dataSource
        flyway.clean()
        flyway.migrate()
    }

    fun prepareDatabase(vararg operations: (DSLContext) -> Unit) {
        operations.forEach { operation -> operation(db) }
    }
}
