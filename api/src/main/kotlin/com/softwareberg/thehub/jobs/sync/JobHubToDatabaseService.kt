package com.softwareberg.thehub.jobs.sync

import com.softwareberg.thehub.jobs.CompanyRepository
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
class JobHubToDatabaseService(private val repository: CompanyRepository) {

    @Transactional
    fun upsertJob(source: Job) {
        val company = repository.findById(source.company.key).orElse(CompanyEntity())

        val location = LocationEntity()
        location.location = source.locationLabel

        val domain = DomainEntity()
        domain.domain = source.host

        company.companyId = source.company.key
        company.name = source.company.name
        company.logo = source.company.logo.filename
        company.location = location
        company.domain = domain

        val keywords = source.keywords.map { JobKeywordEntity().apply { keyword = it } }

        val perks = source.perks.map {
            val perk = JobPerkEntity()
            perk.jobPerkId = it.key
            perk.description = it.description
            perk
        }

        val equity = if (source.equity != null) EquityEntity().apply { equity = source.equity } else null

        val monthlySalary = if (source.monthlySalary != null) MonthlySalaryEntity().apply { monthlySalary = source.monthlySalary } else null

        val positionType = PositionsTypeEntity()
        positionType.positionType = source.positionType

        val job = JobEntity()
        job.jobId = source.key
        job.companyId = source.company.key
        job.title = source.title
        job.description = source.description
        job.positionType = positionType
        job.equity = equity
        job.monthlySalary = monthlySalary
        job.keywords.clear()
        job.keywords.addAll(keywords)
        job.perks.clear()
        job.perks.addAll(perks)

        company.jobs.add(job)
        repository.save(company)
    }
}
