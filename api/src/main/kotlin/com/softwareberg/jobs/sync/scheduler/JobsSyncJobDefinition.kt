package com.softwareberg.jobs.sync.scheduler

import com.softwareberg.jobs.sync.JobsSyncService
import org.quartz.JobBuilder.newJob
import org.quartz.JobDataMap
import org.quartz.Scheduler
import org.quartz.SimpleScheduleBuilder.simpleSchedule
import org.quartz.TriggerBuilder.newTrigger

class JobsSyncJobDefinition(private val scheduler: Scheduler, private val jobsSyncService: JobsSyncService) {

    fun start() {
        val group = "theHub"

        val jobDataMap = JobDataMap()
        jobDataMap["jobsSyncService"] = jobsSyncService

        val job = newJob(JobsSyncCronJob::class.java)
            .withIdentity("theHubSync", group)
            .usingJobData(jobDataMap)
            .build()

        val trigger = newTrigger()
            .withIdentity("theHubSyncTrigger", group)
            .startNow()
            .withSchedule(simpleSchedule()
                .withIntervalInMinutes(15)
                .repeatForever())
            .build()

        scheduler.scheduleJob(job, trigger)
    }
}
