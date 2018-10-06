package com.softwareberg.jobs.sync

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.softwareberg.HttpClient
import com.softwareberg.HttpResponse
import com.softwareberg.JsonMapper
import com.softwareberg.jobs.sync.JobApiFetcher.JobsWrapper
import com.softwareberg.readText
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Test
import java.net.HttpURLConnection.HTTP_OK
import java.util.concurrent.CompletableFuture.completedFuture

class JobApiFetcherTest {

    private val jsonMapper = JsonMapper.create()
    private val httpClient = mock<HttpClient>()
    private val jobFetcher = JobApiFetcher(httpClient, jsonMapper)

    @Test
    fun `it should fetch all jobs`() {
        // given
        val host = "hub.no"
        whenever(httpClient.execute(any())).doReturn(
            responseWithBodyFromFile("/http/jobs/no_page_1.json"),
            responseWithBodyFromFile("/http/jobs/no_page_1.json"),
            responseWithBodyFromFile("/http/jobs/no_page_2.json")
        )

        // when
        val jobs = jobFetcher.fetch(host)

        // then
        assertThatJobsHaveRightSize(jobs)
        assertThatSampleJobIsValid(jobs)
        assertThatAllJobsHaveValidStructure(jobs)
    }

    private fun assertThatJobsHaveRightSize(jobs: List<JobsWrapper>) {
        assertThat(jobs).hasSize(2)
        assertThat(jobs[0].jobs.docs).hasSize(15)
        assertThat(jobs[1].jobs.docs).hasSize(15)
    }

    private fun assertThatSampleJobIsValid(jobs: List<JobsWrapper>) {
        val job = jobs.flatMap { it.jobs.docs }.find { it.key == "god-pa-elkraft-og-lyst-til-a-prove-deg-som-radgiver" } ?: throw IllegalStateException()
        assertThat(job.title).isEqualTo("God på elkraft, og lyst til å prøve deg som rådgiver?")
        assertThat(job.key).isEqualTo("god-pa-elkraft-og-lyst-til-a-prove-deg-som-radgiver")
        assertThat(job.locationLabel).isEqualTo("Oslo")
        assertThat(job.jobPositionTypes.first().name).isEqualTo("Internship")
        assertThat(job.company.name).isEqualTo("PQA")
        assertThat(job.company.logo.filename).isEqualTo("5a58d02e3dee59b26abcbe26/logo_upload-335855270c8d4ceae07b4d30c010d408.jpg")
    }

    private fun assertThatAllJobsHaveValidStructure(jobsWrapper: List<JobsWrapper>) {
        val jobs = jobsWrapper.flatMap { it.jobs.docs }
        val softly = SoftAssertions()
        jobs.forEach { job ->
            softly.assertThat(job.title).isNotBlank
            softly.assertThat(job.key).isNotBlank
            softly.assertThat(job.locationLabel).isNotBlank
            softly.assertThat(job.jobPositionTypes.first().name).isNotBlank
            softly.assertThat(job.company.name).isNotBlank
            softly.assertThat(job.company.logo.filename).isNotBlank
        }
        softly.assertAll()
    }

    private fun responseWithBodyFromFile(path: String) = completedFuture(HttpResponse(HTTP_OK, body = readText(path)))
}
