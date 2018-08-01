package com.softwareberg.configuration

import com.google.inject.Guice
import com.google.inject.Injector
import com.softwareberg.cron.CronModule

object Configuration {

    val injector = createInjector()

    private fun createInjector(): Injector {
        return Guice.createInjector(
            ConfigModule(),
            ErrorsControllerModule(),
            StaticFilesModule(),
            HttpClientModule(),
            HttpServerModule(),
            JsonXmlModule(),
            DatabaseModule(),
            HubModule(),
            CronModule()
        )
    }
}
