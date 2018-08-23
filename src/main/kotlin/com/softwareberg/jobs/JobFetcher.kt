package com.softwareberg.jobs

class JobFetcher(private val jonFetcher: JobApiFetcher, private val jobHtmlFetcher: JobHtmlFetcher) {

    fun featch(): List<Job> {
        val pages = jonFetcher.fetch()
        val jobsFromApi = pages.flatMap { it.jobs.docs }
        return jobsFromApi.map { api ->
            val html = jobHtmlFetcher.fetchDetails(api.key)
            Job(
                api.key,
                api.positionType,
                api.locationLabel,
                api.title,
                api.company,
                html.monthlySalary,
                html.equity,
                html.keywords,
                html.description,
                html.perks
            )
        }
    }
}
