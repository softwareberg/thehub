package com.softwareberg.base

import com.google.inject.Guice
import com.google.inject.Injector
import com.softwareberg.base.modules.ConfigModule
import com.softwareberg.base.modules.DatabaseModule
import com.softwareberg.base.modules.JsonXmlModule
import com.softwareberg.scheduler.SchedulerModule
import com.softwareberg.hub.HubModule
import com.softwareberg.web.ErrorsControllerModule
import com.softwareberg.web.HttpClientModule
import com.softwareberg.web.HttpServerModule
import com.softwareberg.web.StaticFilesModule

object ApplicationContext {

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
            SchedulerModule()
        )
    }
}
