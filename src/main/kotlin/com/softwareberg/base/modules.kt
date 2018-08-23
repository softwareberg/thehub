package com.softwareberg.base

import com.softwareberg.Database
import com.softwareberg.JsonMapper
import com.softwareberg.XmlMapper
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import javax.sql.DataSource

val configModule = Kodein.Module("configModule") {
    bind<Config>() with singleton { ConfigFactory.load() }
}

val databaseModule = Kodein.Module("databaseModule") {
    bind<DataSource>() with singleton {
        val config: Config = instance()
        val hikariConfig = HikariConfig()
        hikariConfig.jdbcUrl = config.getString("datasource.jdbcUrl")
        hikariConfig.username = config.getString("datasource.username")
        hikariConfig.password = config.getString("datasource.password")
        HikariDataSource(hikariConfig)
    }

    bind<Database>() with singleton { Database(instance()) }

    bind<Flyway>() with singleton {
        val flyway = Flyway()
        flyway.dataSource = instance()
        flyway
    }
}

val jsonXmlModule = Kodein.Module("jsonXmlModule") {
    bind<JsonMapper>() with singleton { JsonMapper.create() }
    bind<XmlMapper>() with singleton { XmlMapper.create() }
}
