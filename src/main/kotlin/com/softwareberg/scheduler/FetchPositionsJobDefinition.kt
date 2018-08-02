package com.softwareberg.scheduler

import com.softwareberg.hub.HubFetcher
import org.quartz.JobBuilder.newJob
import org.quartz.JobDataMap
import org.quartz.Scheduler
import org.quartz.SimpleScheduleBuilder.simpleSchedule
import org.quartz.TriggerBuilder.newTrigger


class FetchPositionsJobDefinition(private val scheduler: Scheduler, private val hubFetcher: HubFetcher) {

    fun start() {
        // define the job and tie it to our HelloJob class
        val group = "fetchPositionsJob"

        val jobDataMap = JobDataMap()
        jobDataMap["hubFetcher"] = hubFetcher

        val job = newJob(FetchPositionsJob::class.java)
            .withIdentity("no", group)
            .usingJobData(jobDataMap)
            .build()

        val trigger = newTrigger()
            .withIdentity("fetchPositionsJobTrigger", group)
            .startNow()
            .withSchedule(simpleSchedule()
                .withIntervalInMinutes(3)
                .repeatForever())
            .build()

        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, trigger)
    }
}
