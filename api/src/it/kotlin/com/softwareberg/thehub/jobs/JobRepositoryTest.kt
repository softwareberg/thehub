package com.softwareberg.thehub.jobs

import com.softwareberg.thehub.base.DatabaseSetup
import com.softwareberg.thehub.base.deleteAll
import com.softwareberg.thehub.base.insertCompany
import com.softwareberg.thehub.base.insertDomain
import com.softwareberg.thehub.base.insertLocation
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
    fun `it should create company with jobs (reusing)`() {
        // given
        databaseSetup.prepareDatabase(
            deleteAll(),
            insertLocation(location = "Warszawa"),
            insertDomain(domain = "pl"),
            insertCompany(companyId = "company.companyId", domain = "pl", location = "Warszawa")
        )
        val company = createCompany()

        // when
        repository.save(company)

        // then
        assertThat(repository.findById("company.companyId").get().domain.domain).isEmpty()
    }

    private fun createCompany(): CompanyEntity {
        val location = LocationEntity()
        location.location = "location.location"

        val domain = DomainEntity()
        domain.domain = "domain.domain"

        val company = CompanyEntity()
        company.companyId = "company.companyId"
        company.name = "company.name"
        company.logo = "company.logo"
        company.location = location
        company.domain = domain

        val keyword = JobKeywordEntity()
        keyword.keyword = "keyword.keyword"

        val perk = JobPerkEntity()
        perk.jobPerkId = "jobPerk.jobPerkId"
        perk.description = "jobPerk.description"

        val equity = EquityEntity()
        equity.equity = "equity.equity"

        val monthlySalary = MonthlySalaryEntity()
        monthlySalary.monthlySalary = "monthlySalary.monthlySalary"

        val positionType = PositionsTypeEntity()
        positionType.positionType = "positionType.positionType"

        val job = JobEntity()
        job.jobId = "job.jobId"
        job.title = "job.title"
        job.description = "job.description"
        job.companyId = company.companyId
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
