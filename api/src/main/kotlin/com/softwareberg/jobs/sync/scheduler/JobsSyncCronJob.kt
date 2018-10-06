package com.softwareberg.jobs.sync.scheduler

import com.softwareberg.jobs.sync.JobsSyncService
import org.quartz.Job
import org.quartz.JobExecutionContext

class JobsSyncCronJob : Job {

    private lateinit var jobsSyncService: JobsSyncService

    fun setJobsSyncService(jobsSyncService: JobsSyncService) {
        this.jobsSyncService = jobsSyncService
    }

    override fun execute(context: JobExecutionContext) {
        jobsSyncService.sync()
    }
}
