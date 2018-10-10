package com.softwareberg.thehub.jobs

import com.softwareberg.thehub.base.DatabaseSetup
import com.softwareberg.thehub.base.deleteAll
import com.softwareberg.thehub.jobs.model.CompanyEntity
import com.softwareberg.thehub.jobs.model.DomainEntity
import com.softwareberg.thehub.jobs.model.EquityEntity
import com.softwareberg.thehub.jobs.model.JobEntity
import com.softwareberg.thehub.jobs.model.JobKeywordEntity
import com.softwareberg.thehub.jobs.model.JobPerkEntity
import com.softwareberg.thehub.jobs.model.LocationEntity
import com.softwareberg.thehub.jobs.model.MonthlySalaryEntity
import com.softwareberg.thehub.jobs.model.PositionsTypeEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional

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

    private fun createCompany(
        companyId: String = "companyId",
        companyName: String = "companyName",
        companyLogo: String = "companyLogo",
        companyDomain: String = "companyDomain",
        companyLocation: String = "companyLocation",
        jobId: String = "jobId",
        jobTitle: String = "jobTitle",
        jobDescription: String = "jobDescription",
        jobKeyword: String = "jobKeyword",
        jobPerkId: String = "jobPerkId",
        jobPerkDescription: String = "jobPerkDescription",
        jobEquity: String = "jobEquity",
        jobMonthlySalary: String = "jobMonthlySalary",
        jobPositionType: String = "jobPositionType"
    ): CompanyEntity {
        val company = repository.findById(companyId).orElse(CompanyEntity())

        val location = LocationEntity()
        location.location = companyLocation

        val domain = DomainEntity()
        domain.domain = companyDomain

        company.companyId = companyId
        company.name = companyName
        company.logo = companyLogo
        company.location = location
        company.domain = domain

        val keyword = JobKeywordEntity()
        keyword.keyword = jobKeyword

        val perk = JobPerkEntity()
        perk.jobPerkId = jobPerkId
        perk.description = jobPerkDescription

        val equity = EquityEntity()
        equity.equity = jobEquity

        val monthlySalary = MonthlySalaryEntity()
        monthlySalary.monthlySalary = jobMonthlySalary

        val positionType = PositionsTypeEntity()
        positionType.positionType = jobPositionType

        val job = JobEntity()
        job.jobId = jobId
        job.companyId = companyId
        job.title = jobTitle
        job.description = jobDescription
        job.positionType = positionType
        job.equity = equity
        job.monthlySalary = monthlySalary
        job.keywords.clear()
        job.keywords.add(keyword)
        job.perks.clear()
        job.perks.add(perk)

        company.jobs.add(job)

        return company
    }
}
