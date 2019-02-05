package com.softwareberg.thehub.jobs.sync

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class JobsSyncServiceScheduler(private val jobsSyncService: JobsSyncService) {

    @Scheduled(fixedRateString = "\${thehub.sync.scheduling.fixedRate-in-milliseconds}")
    fun synchronizeJobs() {
        jobsSyncService.synchronizeJobs()
    }
}
