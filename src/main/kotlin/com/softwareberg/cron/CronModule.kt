package com.softwareberg.cron

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.softwareberg.hub.HubFetcher
import org.quartz.Scheduler
import org.quartz.impl.StdSchedulerFactory
import javax.inject.Singleton

class CronModule : AbstractModule() {

    @Singleton
    @Provides
    private fun provideScheduler(): Scheduler {
        val schedulerFactory = StdSchedulerFactory()
        val scheduler = schedulerFactory.scheduler
        scheduler.start()
        return scheduler
    }

    @Singleton
    @Provides
    private fun provideFetchPositionsCronJobDefinition(scheduler: Scheduler, hubFetcher: HubFetcher): FetchPositionsCronJobDefinition {
        return FetchPositionsCronJobDefinition(scheduler, hubFetcher)
    }
}
