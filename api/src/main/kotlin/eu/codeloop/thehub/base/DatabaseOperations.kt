package eu.codeloop.thehub.base

import eu.codeloop.thehub.jooq.Tables.COMPANIES
import eu.codeloop.thehub.jooq.Tables.EQUITIES
import eu.codeloop.thehub.jooq.Tables.JOBS
import eu.codeloop.thehub.jooq.Tables.LOCATIONS
import eu.codeloop.thehub.jooq.Tables.MONTHLY_SALARIES
import org.jooq.DSLContext
import java.time.OffsetDateTime
import java.time.ZoneOffset.UTC

@SuppressWarnings("TooManyFunctions")
class DatabaseOperations(
    private val db: DSLContext,
    private val now: () -> OffsetDateTime = { OffsetDateTime.now(UTC) }
) {

    fun upsertSalary(monthlySalary: String) {
        val record = db.fetchOne(MONTHLY_SALARIES, MONTHLY_SALARIES.MONTHLY_SALARY.eq(monthlySalary)) ?: db.newRecord(MONTHLY_SALARIES)
        record.monthlySalary = monthlySalary
        if (record != record.original()) {
            record.dateModified = now()
        }
        record.store()
    }

    fun upsertEquity(equity: String) {
        val record = db.fetchOne(EQUITIES, EQUITIES.EQUITY.eq(equity)) ?: db.newRecord(EQUITIES)
        record.equity = equity
        if (record != record.original()) {
            record.dateModified = now()
        }
        record.store()
    }

    fun upsertLocation(location: String) {
        val record = db.fetchOne(LOCATIONS, LOCATIONS.LOCATION.eq(location)) ?: db.newRecord(LOCATIONS)
        record.location = location
        if (record != record.original()) {
            record.dateModified = now()
        }
        record.store()
    }

    fun upsertCompany(companyId: String, name: String, logo: String, location: String) {
        val record = db.fetchOne(COMPANIES, COMPANIES.COMPANY_ID.eq(companyId)) ?: db.newRecord(COMPANIES)
        record.companyId = companyId
        record.name = name
        record.logo = logo
        record.location = location
        if (record != record.original()) {
            record.dateModified = now()
        }
        record.store()
    }

    @SuppressWarnings("LongParameterList")
    fun upsertJob(
        jobId: String,
        companyId: String,
        title: String,
        description: String,
        monthlySalary: String?,
        equity: String?,
        hasStar: Boolean,
        isDeleted: Boolean,
        approvedAt: OffsetDateTime
    ) {
        val record = db.fetchOne(JOBS, JOBS.JOB_ID.eq(jobId)) ?: db.newRecord(JOBS)
        record.jobId = jobId
        record.companyId = companyId
        record.title = title
        record.monthlySalary = monthlySalary
        record.equity = equity
        record.description = description
        record.hasStar = hasStar
        record.isDeleted = isDeleted
        record.approvedAt = approvedAt
        if (record != record.original()) {
            record.dateModified = now()
        }
        record.store()
    }
}
