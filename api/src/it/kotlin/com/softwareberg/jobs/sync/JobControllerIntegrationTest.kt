package com.softwareberg.jobs.sync

import com.despegar.sparkjava.test.SparkServer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.softwareberg.JsonMapper
import com.softwareberg.base.JsonPathAssert.Companion.assertJsonThat
import com.softwareberg.base.bodyAsString
import com.softwareberg.base.get
import com.softwareberg.jobs.Job
import com.softwareberg.jobs.JobController
import com.softwareberg.jobs.JobRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.ClassRule
import org.junit.Test
import spark.servlet.SparkApplication
import java.net.HttpURLConnection.HTTP_OK

class JobControllerIntegrationTest {

    class TestSparkApplication : SparkApplication {
        override fun init() {
            val jsonMapper = JsonMapper.create()
            JobController(jobRepository, jsonMapper).start()
        }
    }

    companion object {

        private val jobRepository = mock<JobRepository>()

        @JvmField
        @ClassRule
        val testServer = SparkServer(TestSparkApplication::class.java, 4567)
    }

    @Test
    fun `it should do return all jobs`() {
        // given
        whenever(jobRepository.findAll(any(), any())).doReturn(listOf(createJob("foo"), createJob("bar")))

        // when
        val resp = testServer.get("/api/jobs")

        // then
        assertThat(resp.code()).isEqualTo(HTTP_OK)
        assertJsonThat(resp.bodyAsString()).jsonPathAsListOfStrings("$.data[*].jobId").containsExactly("foo", "bar")
    }

    private fun createJob(jobId: String): Job = Job(
        jobId,
        "Software Developer",
        "Software Developer",
        false,
        listOf("java", "kotlin"),
        "http://googe.com"
    )
}
