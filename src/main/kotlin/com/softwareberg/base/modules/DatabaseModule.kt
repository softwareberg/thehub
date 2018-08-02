package com.softwareberg.base.modules

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.softwareberg.Database
import com.typesafe.config.Config
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import javax.inject.Singleton
import javax.sql.DataSource

class DatabaseModule : AbstractModule() {

    override fun configure() {}

    @Singleton
    @Provides
    fun provideDataSource(config: Config): DataSource {
        val hikariConfig = HikariConfig()
        hikariConfig.jdbcUrl = config.getString("datasource.jdbcUrl")
        hikariConfig.username = config.getString("datasource.username")
        hikariConfig.password = config.getString("datasource.password")
        return HikariDataSource(hikariConfig)
    }

    @Singleton
    @Provides
    private fun provideDatabase(dataSource: DataSource): Database {
        return Database(dataSource)
    }

    @Singleton
    @Provides
    private fun provideFlyway(dataSource: DataSource): Flyway {
        val flyway = Flyway()
        flyway.dataSource = dataSource
        return flyway
    }
}
