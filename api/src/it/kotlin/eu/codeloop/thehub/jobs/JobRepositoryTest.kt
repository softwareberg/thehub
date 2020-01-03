package eu.codeloop.thehub.jobs

import eu.codeloop.thehub.base.DatabaseSetup
import eu.codeloop.thehub.base.DatabaseSetupOperations.deleteAll
import eu.codeloop.thehub.jobs.model.CompanyEntity
import eu.codeloop.thehub.jobs.model.EquityEntity
import eu.codeloop.thehub.jobs.model.JobEntity
import eu.codeloop.thehub.jobs.model.LocationEntity
import eu.codeloop.thehub.jobs.model.MonthlySalaryEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Transactional
@RunWith(SpringRunner::class)
@SpringBootTest
class JobRepositoryTest {

    @Autowired
    private lateinit var databaseSetup: DatabaseSetup

    @Autowired
    private lateinit var repository: CompanyRepository

    @Test
    fun `it should create company with jobs`() {
        // given
        databaseSetup.prepareDatabase(
            deleteAll()
        )
        val company = createCompany()

        // when
        repository.save(company)

        // then
        assertThat(repository.count()).isEqualTo(1)
    }

    @Test
    fun `it should create company with jobs - fix multiple representations of the same entity error`() {
        // given
        databaseSetup.prepareDatabase(
            deleteAll()
        )

        // when
        repository.save(createCompany(companyId = "foo", jobId = "bar"))
        repository.save(createCompany(companyId = "foo", jobId = "baz"))

        // then
        assertThat(repository.count()).isEqualTo(1)
        assertThat(repository.findById("foo").get().jobs).hasSize(2)
    }

    @SuppressWarnings("LongParameterList")
    private fun createCompany(
        companyId: String = "companyId",
        companyName: String = "companyName",
        companyLogo: String = "companyLogo",
        companyLocation: String = "companyLocation",
        jobId: String = "jobId",
        jobTitle: String = "jobTitle",
        jobDescription: String = "jobDescription",
        jobEquity: String = "jobEquity",
        jobMonthlySalary: String = "jobMonthlySalary"
    ): CompanyEntity {
        val company = repository.findById(companyId).orElse(CompanyEntity())

        val location = LocationEntity()
        location.location = companyLocation

        company.companyId = companyId
        company.name = companyName
        company.logo = companyLogo
        company.location = location

        val equity = EquityEntity()
        equity.equity = jobEquity

        val monthlySalary = MonthlySalaryEntity()
        monthlySalary.monthlySalary = jobMonthlySalary

        val job = JobEntity()
        job.jobId = jobId
        job.companyId = companyId
        job.title = jobTitle
        job.description = jobDescription
        job.equity = equity
        job.monthlySalary = monthlySalary
        job.approvedAt = OffsetDateTime.now()

        company.jobs.add(job)

        return company
    }
}
