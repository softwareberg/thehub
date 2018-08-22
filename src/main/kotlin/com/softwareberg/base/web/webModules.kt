package com.softwareberg.base.web

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.softwareberg.HttpClient
import com.softwareberg.JsonMapper
import com.softwareberg.SimpleHttpClient
import com.softwareberg.companies.HubController
import com.softwareberg.base.web.errors.ErrorsController
import com.typesafe.config.Config
import org.asynchttpclient.DefaultAsyncHttpClient
import javax.inject.Singleton

class StaticFilesModule : AbstractModule() {

    override fun configure() {
    }

    @Singleton
    @Provides
    private fun provideStaticFilesController(): StaticFilesController {
        return StaticFilesController()
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

class HttpClientModule : AbstractModule() {

    override fun configure() {}

    @Singleton
    @Provides
    private fun provideHttpClient(): HttpClient {
        val asyncHttpClient = DefaultAsyncHttpClient()
        return SimpleHttpClient(asyncHttpClient)
    }
}
