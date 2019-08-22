package eu.codeloop.thehub.jobs.sync

import eu.codeloop.thehub.base.DatabaseOperations
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Service

@Service
class JobHubToDatabaseService(private val db: DSLContext) {

    fun upsertJob(job: Job) {
        db.transaction { configuration ->
            val context = DSL.using(configuration)
            val databaseOperations = DatabaseOperations(context)
            databaseOperations.upsertDomain(job.host)
            databaseOperations.upsertLocation(job.locationLabel)
            databaseOperations.upsertPositionType(job.positionType)
            job.keywords.forEach(databaseOperations::upsertKeyword)
            job.perks.forEach { perk -> databaseOperations.upsertPerk(perk.key, perk.description) }
            databaseOperations.upsertCompany(job.company.key, job.company.name, job.company.logo.filename, job.host, job.locationLabel)
            job.equity?.also(databaseOperations::upsertEquity)
            job.monthlySalary?.also(databaseOperations::upsertMonthlySalary)
            databaseOperations.upsertJob(
                job.key, job.company.key, job.title, job.description, job.monthlySalary, job.equity, job.positionType, hasStar = false, isDeleted = false, approvedAt = job.approvedAt, poster = job.poster.filename)
            job.keywords.forEach { keyword -> databaseOperations.upsertJobKeyword(keyword, job.key) }
            job.perks.forEach { perk -> databaseOperations.upsertJobPerk(perk.key, job.key) }
        }
    }
}
