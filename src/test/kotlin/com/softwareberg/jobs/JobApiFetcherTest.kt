package com.softwareberg.jobs

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.softwareberg.HttpClient
import com.softwareberg.HttpResponse
import com.softwareberg.JsonMapper
import com.softwareberg.jobs.JobApiFetcher.JobsWrapper
import com.softwareberg.readFile
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
        whenever(httpClient.execute(any())).doReturn(
            responseWithBodyFromFile("/http/jobs/no_page_1.json"),
            responseWithBodyFromFile("/http/jobs/no_page_1.json"),
            responseWithBodyFromFile("/http/jobs/no_page_2.json")
        )

        // when
        val jobs = jobFetcher.fetch()

        // then
        assertThatJobsHaveRightSize(jobs)
        assertThatFirstJobIsValid(jobs)
        assertThatAllJobsHaveValidStructure(jobs)
    }

    private fun assertThatJobsHaveRightSize(jobs: List<JobsWrapper>) {
        assertThat(jobs).hasSize(2)
        assertThat(jobs[0].jobs.docs).hasSize(15)
        assertThat(jobs[1].jobs.docs).hasSize(15)
    }

    private fun assertThatFirstJobIsValid(jobs: List<JobsWrapper>) {
        val job = jobs.first().jobs.docs.first()
        assertThat(job.title).isEqualTo("Social Media Specialist Wanted!")
        assertThat(job.key).isEqualTo("social-media-specialist-wanted")
        assertThat(job.locationLabel).isEqualTo("Oslo")
        assertThat(job.positionType).isEqualTo("freelance")
        assertThat(job.company.name).isEqualTo("Grundr AS")
        assertThat(job.company.logo.filename).isEqualTo("584ab8a72c2e49901c133a5a/logo_upload-9c07c1f18740873ab478aeda28891d71.png")
    }

    private fun assertThatAllJobsHaveValidStructure(jobsWrapper: List<JobsWrapper>) {
        val jobs = jobsWrapper.flatMap { it.jobs.docs }
        val softly = SoftAssertions()
        jobs.forEach { job ->
            softly.assertThat(job.title).isNotBlank
            softly.assertThat(job.key).isNotBlank
            softly.assertThat(job.locationLabel).isNotBlank
            softly.assertThat(job.positionType).isNotBlank
            softly.assertThat(job.company.name).isNotBlank
            softly.assertThat(job.company.logo.filename).isNotBlank
        }
        softly.assertAll()
    }

    private fun responseWithBodyFromFile(path: String) = completedFuture(HttpResponse(HTTP_OK, body = readFile(path)))
}
