package com.softwareberg.jobs.sync

class JobFetcher(private val jonFetcher: JobApiFetcher, private val jobHtmlFetcher: JobHtmlFetcher) {

    fun fetch(host: String): List<Job> {
        val pages = jonFetcher.fetch(host)
        val jobsFromApi = pages.flatMap { it.jobs.docs }
        return jobsFromApi.map { api ->
            val html = jobHtmlFetcher.fetchDetails(host, api.key)
            Job(
                api.key,
                api.positionType ?: "unknown",
                api.locationLabel,
                api.title,
                api.company,
                html.monthlySalary,
                html.equity,
                html.keywords,
                html.description,
                html.perks,
                host
            )
        }
    }
}
