package com.softwareberg.thehub.jobs.sync

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class JobsSyncService(private val jobFetcher: JobFetcher, private val jobHubToDatabaseService: JobHubToDatabaseService) {

    @Scheduled(fixedRateString = "\${thehub.scheduling.sync.fixedRate-in-milliseconds}")
    fun sync() {
        sync("thehub.dk")
    }

    private fun sync(host: String) {
        val jobs = jobFetcher.fetch(host)
        jobs.forEach(jobHubToDatabaseService::upsertJob)
    }
}
