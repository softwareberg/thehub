package com.softwareberg.jobs.sync

import com.softwareberg.HttpClient
import com.softwareberg.HttpMethod
import com.softwareberg.HttpRequest
import com.softwareberg.JsonMapper
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.future.await
import kotlinx.coroutines.experimental.runBlocking
import org.slf4j.LoggerFactory

class JobApiFetcher(private val http: HttpClient, private val json: JsonMapper) {

    private val log = LoggerFactory.getLogger(JobApiFetcher::class.java)

    fun fetch(host: String): List<JobsWrapper> = runBlocking(CommonPool) {
        fetchAsync(host).await()
    }

    private fun fetchAsync(host: String): Deferred<List<JobsWrapper>> = async(CommonPool) {
        val firstJobsPage = fetchOnePage(host, 1).await()
        val lastPage = firstJobsPage.jobs.pages
        fetchPageRange(host, lastPage).await()
    }

    private fun fetchOnePage(host: String, page: Int): Deferred<JobsWrapper> = async(CommonPool) {
        val url = "https://$host/api/jobs?page=$page"
        log.info("fetching $url...")
        val request = HttpRequest(HttpMethod.GET, url)
        val response = http.execute(request).await()
        val body = response.body ?: throw IllegalStateException("req: $request")
        json.read<JobsWrapper>(body)
    }

    private fun fetchPageRange(host: String, lastPage: Int): Deferred<List<JobsWrapper>> = async(CommonPool) {
        (1..lastPage).map { fetchOnePage(host, it) }.map { it.await() }
    }

    data class JobsWrapper(val jobs: Jobs)
    data class Jobs(val pages: Int, val docs: List<Job>)
    data class Job(
        val key: String,
        val positionType: String?,
        val locationLabel: String,
        val title: String,
        val company: Company
    )
}
