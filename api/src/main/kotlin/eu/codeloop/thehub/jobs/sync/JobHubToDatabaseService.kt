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
            databaseOperations.upsertLocation(job.locationLabel)
            databaseOperations.upsertCompany(job.company.key, job.company.name, job.company.logoImage.path, job.locationLabel)
            databaseOperations.upsertEquity(job.equity)
            databaseOperations.upsertSalary(job.salary)
            databaseOperations.upsertJob(job.key, job.company.key, job.title, job.description, job.salary, job.equity, hasStar = false, isDeleted = false, approvedAt = job.createdAt)
        }
    }
}
