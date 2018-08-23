package com.softwareberg.scheduler

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.quartz.Scheduler
import org.quartz.impl.StdSchedulerFactory

val schedulerModule = Kodein.Module("schedulerModule") {

    bind<Scheduler>() with singleton {
        val schedulerFactory = StdSchedulerFactory()
        val scheduler = schedulerFactory.scheduler
        scheduler.start()
        scheduler
    }

    bind<FetchPositionsJobDefinition>() with singleton { FetchPositionsJobDefinition(instance(), instance()) }
}
