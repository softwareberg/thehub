package com.softwareberg.jobs.sync

class JobsSyncService(private val config: SyncConfig, private val jobFetcher: JobFetcher, private val jobHubToDatabaseService: JobHubToDatabaseService) {

    fun sync() {
        config.hosts.forEach(this::sync)
    }

    private fun sync(host: String) {
        val jobs = jobFetcher.fetch(host)
        jobs.forEach(jobHubToDatabaseService::upserJob)
    }
}
