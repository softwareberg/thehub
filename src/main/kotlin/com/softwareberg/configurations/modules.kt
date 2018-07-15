package com.softwareberg.configurations

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.softwareberg.Database
import com.softwareberg.HttpClient
import com.softwareberg.JsonMapper
import com.softwareberg.SimpleHttpClient
import com.softwareberg.XmlMapper
import com.softwareberg.hub.HubController
import com.softwareberg.hub.HubFetcher
import com.softwareberg.web.HttpServer
import com.softwareberg.web.StaticFilesController
import com.softwareberg.web.errors.ErrorsController
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.asynchttpclient.DefaultAsyncHttpClient
import org.flywaydb.core.Flyway
import org.h2.tools.Server
import javax.inject.Singleton
import javax.sql.DataSource

class HttpClientModule : AbstractModule() {

    override fun configure() {}

    @Singleton
    @Provides
    private fun provideHttpClient(): HttpClient {
        val asyncHttpClient = DefaultAsyncHttpClient()
        return SimpleHttpClient(asyncHttpClient)
    }
}

class JsonXmlModule : AbstractModule() {

    override fun configure() {}

    @Singleton
    @Provides
    private fun provideJsonMapper(): JsonMapper {
        return JsonMapper.create()
    }

    @Singleton
    @Provides
    private fun provideXmlMapper(): XmlMapper {
        return XmlMapper.create()
    }
}

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

    @Singleton
    @Provides
    private fun provideH2Database(): Server {
        return Server.createTcpServer()
    }
}

class ConfigModule : AbstractModule() {

    override fun configure() {}

    @Singleton
    @Provides
    private fun provideConfig(): Config {
        return ConfigFactory.load()
    }
}

class StaticFilesModule : AbstractModule() {

    override fun configure() {
    }

    @Singleton
    @Provides
    private fun provideStaticFilesController(): StaticFilesController {
        return StaticFilesController()
    }
}

class HubModule : AbstractModule() {

    override fun configure() {}

    @Singleton
    @Provides
    private fun provideHubController(httpClient: HttpClient, jsonMapper: JsonMapper): HubController {
        val hubFetcher = HubFetcher(httpClient, jsonMapper)
        return HubController(jsonMapper, hubFetcher)
    }
}

class HttpServerModule : AbstractModule() {

    override fun configure() {}

    @Singleton
    @Provides
    private fun provideHttpServer(
        serverConfiguration: ServerConfiguration,
        errorsController: ErrorsController,
        staticFilesController: StaticFilesController,
        hubController: HubController
    ): HttpServer {
        return HttpServer(
            serverConfiguration,
            errorsController,
            staticFilesController,
            hubController
        )
    }

    @Singleton
    @Provides
    private fun provideServerConfiguration(config: Config): ServerConfiguration {
        val port = config.getInt("server.port")
        return ServerConfiguration(port)
    }
}

class ErrorsControllerModule : AbstractModule() {

    override fun configure() {}

    @Singleton
    @Provides
    private fun provideErrorsController(jsonMapper: JsonMapper): ErrorsController {
        return ErrorsController(jsonMapper)
    }
}
