package com.softwareberg.base.web

import com.softwareberg.HttpClient
import com.softwareberg.SimpleHttpClient
import com.softwareberg.base.web.errors.ErrorsController
import com.typesafe.config.Config
import org.asynchttpclient.DefaultAsyncHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.allInstances
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val httpServerModule = Kodein.Module("httpServerModule") {
    bind<HttpServer>() with singleton { HttpServer(instance(), allInstances()) }

    bind<ServerConfiguration>() with singleton {
        val config: Config = instance()
        val port = config.getInt("server.port")
        ServerConfiguration(port)
    }

    bind<HealthController>() with singleton { HealthController(instance()) }

    bind<ErrorsController>() with singleton { ErrorsController(instance()) }
}

val httpClientModule = Kodein.Module("httpClientModule") {
    bind<HttpClient>() with singleton { SimpleHttpClient(DefaultAsyncHttpClient()) }
}
