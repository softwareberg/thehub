package com.softwareberg.jobs

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

class JobFetcher(
    private val http: HttpClient,
    private val json: JsonMapper
) {

    private val log = LoggerFactory.getLogger(JobFetcher::class.java)

    fun fetchJobs(): List<JobsWrapper> = runBlocking(CommonPool) {
        fetchJobsAsync().await()
    }

    private fun fetchJobsAsync(): Deferred<List<JobsWrapper>> = async(CommonPool) {
        val firstJobsPage = fetchJobs(1).await()
        val lastPage = firstJobsPage.jobs.pages
        fetchAllJobs(lastPage).await()
    }

    private fun fetchJobs(page: Int): Deferred<JobsWrapper> = async(CommonPool) {
        val url = "https://thehub.dk/api/jobs?page=$page"
        log.info("fetching $url...")
        val request = HttpRequest(HttpMethod.GET, url)
        val response = http.execute(request).await()
        val body = response.body ?: throw IllegalStateException("req: $request")
        json.read<JobsWrapper>(body)
    }

    private fun fetchAllJobs(lastPage: Int): Deferred<List<JobsWrapper>> = async(CommonPool) {
        (1..lastPage).map { fetchJobs(it) }.map { it.await() }
    }
}
