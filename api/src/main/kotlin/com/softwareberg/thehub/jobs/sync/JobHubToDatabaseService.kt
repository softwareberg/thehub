package com.softwareberg.thehub.jobs.sync

import com.softwareberg.thehub.jobs.JobRepository
import com.softwareberg.thehub.jobs.model.CompanyEntity
import com.softwareberg.thehub.jobs.model.DomainEntity
import com.softwareberg.thehub.jobs.model.EquityEntity
import com.softwareberg.thehub.jobs.model.JobEntity
import com.softwareberg.thehub.jobs.model.JobKeywordEntity
import com.softwareberg.thehub.jobs.model.JobPerkEntity
import com.softwareberg.thehub.jobs.model.LocationEntity
import com.softwareberg.thehub.jobs.model.MonthlySalaryEntity
import com.softwareberg.thehub.jobs.model.PositionsTypeEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class JobHubToDatabaseService(private val jobRepository: JobRepository) {

    @Transactional
    fun upsertJob(source: Job) {
        val location = LocationEntity()
        location.location = source.locationLabel

        val domain = DomainEntity()
        domain.domain = source.host

        val company = CompanyEntity()
        company.companyId = source.company.key
        company.name = source.company.name
        company.logo = source.company.logo.filename
        company.location = location
        company.domain = domain

        val keywords = source.keywords.map { JobKeywordEntity().apply { keyword = it } }

        val perks = source.perks.map {
            val jobPerk = JobPerkEntity()
            jobPerk.jobPerkId = it.key
            jobPerk.description = it.description
            jobPerk
        }

        val equity = if (source.equity != null) EquityEntity().apply { equity = source.equity } else null

        val monthlySalary = if (source.monthlySalary != null) MonthlySalaryEntity().apply { monthlySalary = source.monthlySalary } else null

        val positionType = PositionsTypeEntity()
        positionType.positionType = source.positionType

        val job = JobEntity()
        job.jobId = source.key
        job.title = source.title
        job.description = source.description
        job.company = company
        job.positionType = positionType
        job.equity = equity
        job.monthlySalary = monthlySalary
        job.keywords = keywords.toMutableList()
        job.perks = perks.toMutableList()

        jobRepository.save(job)
    }
}
