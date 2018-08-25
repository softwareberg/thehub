package com.softwareberg.jobs.sync

import com.softwareberg.jobs.sync.scheduler.JobsSyncJobDefinition
import com.typesafe.config.Config
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val jobSyncModule = Kodein.Module("jobSyncModule") {
    bind<JsoupFetcher>() with singleton { JsoupFetcher() }
    bind<JobHtmlFetcher>() with singleton { JobHtmlFetcher(instance()) }
    bind<JobApiFetcher>() with singleton { JobApiFetcher(instance(), instance()) }
    bind<JobFetcher>() with singleton { JobFetcher(instance(), instance()) }
    bind<JobHubToDatabaseService>() with singleton { JobHubToDatabaseService(instance()) }
    bind<SyncConfig>() with singleton {
        val config: Config = instance()
        val hosts = config.getStringList("thehub.hosts")
        SyncConfig(hosts)
    }
    bind<JobsSyncService>() with singleton { JobsSyncService(instance(), instance(), instance()) }
    bind<JobsSyncJobDefinition>() with singleton { JobsSyncJobDefinition(instance(), instance()) }
}
