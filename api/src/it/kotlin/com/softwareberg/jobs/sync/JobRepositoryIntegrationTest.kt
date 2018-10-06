package com.softwareberg.jobs.sync

import com.softwareberg.base.DatabaseResource
import com.softwareberg.base.context
import com.softwareberg.base.deleteAll
import com.softwareberg.base.insertCompany
import com.softwareberg.base.insertDomain
import com.softwareberg.base.insertEquity
import com.softwareberg.base.insertJob
import com.softwareberg.base.insertJobKeyword
import com.softwareberg.base.insertKeyword
import com.softwareberg.base.insertLocation
import com.softwareberg.base.insertMonthlySalary
import com.softwareberg.base.insertPositionType
import com.softwareberg.jobs.Job
import com.softwareberg.jobs.JobRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.ClassRule
import org.junit.Test
import org.kodein.di.generic.instance

class JobRepositoryIntegrationTest {

    private val repository: JobRepository by context.instance()

    companion object {

        @JvmField
        @ClassRule
        val dataSourceResource = DatabaseResource()
    }

    @Test
    fun `it should do return all jobs`() {
        // given
        dataSourceResource.prepareDatabase(
            deleteAll(),
            insertPositionType(positionType = "full-time"),
            insertMonthlySalary(monthlySalary = "3000$"),
            insertEquity(equity = "50-50"),
            insertLocation(location = "Warszawa"),
            insertDomain(domain = "pl"),
            insertCompany(companyId = "company-foo", domain = "pl", location = "Warszawa"),
            insertJob(jobId = "job-id-a", companyId = "company-foo", positionType = "full-time", equity = "50-50", monthlySalary = "3000$"),
            insertJob(jobId = "job-id-b", companyId = "company-foo", positionType = "full-time", equity = "50-50", monthlySalary = "3000$")
        )

        // when
        val jobs = repository.findAll(0, 10)

        // then
        assertThat(jobs.map(Job::jobId)).containsExactly("job-id-a", "job-id-b")
    }

    @Test
    fun `it should do search by keyword`() {
        // given
        dataSourceResource.prepareDatabase(
            deleteAll(),
            insertPositionType(positionType = "full-time"),
            insertMonthlySalary(monthlySalary = "3000$"),
            insertEquity(equity = "50-50"),
            insertLocation(location = "Warszawa"),
            insertDomain(domain = "pl"),
            insertCompany(companyId = "company-foo", domain = "pl", location = "Warszawa"),
            insertJob(jobId = "job-id-a", companyId = "company-foo", positionType = "full-time", equity = "50-50", monthlySalary = "3000$"),
            insertJob(jobId = "job-id-b", companyId = "company-foo", positionType = "full-time", equity = "50-50", monthlySalary = "3000$"),
            insertJob(jobId = "job-id-c", companyId = "company-foo", positionType = "full-time", equity = "50-50", monthlySalary = "3000$"),
            insertKeyword("java"),
            insertKeyword("scala"),
            insertKeyword("javascript"),
            insertKeyword("sql"),
            insertJobKeyword("java", "job-id-a"),
            insertJobKeyword("java", "job-id-b"),
            insertJobKeyword("scala", "job-id-a"),
            insertJobKeyword("sql", "job-id-b"),
            insertJobKeyword("javascript", "job-id-c")
        )

        // when
        val java = repository.searchByKeyword("java")
        val scala = repository.searchByKeyword("scala")
        val javascript = repository.searchByKeyword("javascript")
        val sql = repository.searchByKeyword("sql")

        // then
        assertThat(java.map(Job::jobId)).containsExactly("job-id-a", "job-id-b")
        assertThat(scala.map(Job::jobId)).containsExactly("job-id-a")
        assertThat(javascript.map(Job::jobId)).containsExactly("job-id-c")
        assertThat(sql.map(Job::jobId)).containsExactly("job-id-b")
    }
}
