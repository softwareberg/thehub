package com.softwareberg.scheduler

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.softwareberg.companies.CompaniesFetcher
import org.quartz.Scheduler
import org.quartz.impl.StdSchedulerFactory
import javax.inject.Singleton

class SchedulerModule : AbstractModule() {

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
    private fun provideFetchPositionsJobDefinition(
        scheduler: Scheduler,
        companiesFetcher: CompaniesFetcher
    ): FetchPositionsJobDefinition {
        return FetchPositionsJobDefinition(scheduler, companiesFetcher)
    }
}
