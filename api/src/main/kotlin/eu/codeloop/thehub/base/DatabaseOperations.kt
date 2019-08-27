package eu.codeloop.thehub.base

import eu.codeloop.thehub.jooq.Tables.COMPANIES
import eu.codeloop.thehub.jooq.Tables.DOMAINS
import eu.codeloop.thehub.jooq.Tables.EQUITIES
import eu.codeloop.thehub.jooq.Tables.JOBS
import eu.codeloop.thehub.jooq.Tables.JOBS_JOB_KEYWORDS
import eu.codeloop.thehub.jooq.Tables.JOBS_JOB_PERKS
import eu.codeloop.thehub.jooq.Tables.JOB_KEYWORDS
import eu.codeloop.thehub.jooq.Tables.JOB_PERKS
import eu.codeloop.thehub.jooq.Tables.LOCATIONS
import eu.codeloop.thehub.jooq.Tables.MONTHLY_SALARIES
import eu.codeloop.thehub.jooq.Tables.POSITIONS_TYPES
import org.jooq.DSLContext
import java.time.OffsetDateTime
import java.time.ZoneOffset.UTC

@SuppressWarnings("TooManyFunctions")
class DatabaseOperations(
    private val db: DSLContext,
    private val now: () -> OffsetDateTime = { OffsetDateTime.now(UTC) }
) {

    fun upsertMonthlySalary(monthlySalary: String) {
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

    fun upsertPositionType(positionType: String) {
        val record = db.fetchOne(POSITIONS_TYPES, POSITIONS_TYPES.POSITION_TYPE.eq(positionType)) ?: db.newRecord(POSITIONS_TYPES)
        record.positionType = positionType
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

    fun upsertDomain(domain: String) {
        val record = db.fetchOne(DOMAINS, DOMAINS.DOMAIN.eq(domain)) ?: db.newRecord(DOMAINS)
        record.domain = domain
        if (record != record.original()) {
            record.dateModified = now()
        }
        record.store()
    }

    fun upsertCompany(companyId: String, name: String, logo: String, domain: String, location: String) {
        val record = db.fetchOne(COMPANIES, COMPANIES.COMPANY_ID.eq(companyId)) ?: db.newRecord(COMPANIES)
        record.companyId = companyId
        record.name = name
        record.logo = logo
        record.domain = domain
        record.location = location
        if (record != record.original()) {
            record.dateModified = now()
        }
        record.store()
    }

    fun upsertKeyword(keyword: String) {
        val record = db.fetchOne(JOB_KEYWORDS, JOB_KEYWORDS.KEYWORD.eq(keyword)) ?: db.newRecord(JOB_KEYWORDS)
        record.keyword = keyword
        if (record != record.original()) {
            record.dateModified = now()
        }
        record.store()
    }

    fun upsertJobKeyword(keyword: String, jobId: String) {
        val record = db.fetchOne(JOBS_JOB_KEYWORDS, JOBS_JOB_KEYWORDS.JOB_ID.eq(jobId).and(JOBS_JOB_KEYWORDS.KEYWORD.eq(keyword)))
            ?: db.newRecord(JOBS_JOB_KEYWORDS)
        record.keyword = keyword
        record.jobId = jobId
        if (record != record.original()) {
            record.dateModified = now()
        }
        record.store()
    }

    fun upsertPerk(perkId: String, description: String) {
        val record = db.fetchOne(JOB_PERKS, JOB_PERKS.JOB_PERK_ID.eq(perkId)) ?: db.newRecord(JOB_PERKS)
        record.jobPerkId = perkId
        record.description = description
        if (record != record.original()) {
            record.dateModified = now()
        }
        record.store()
    }

    fun upsertJobPerk(perkId: String, jobId: String) {
        val record = db.fetchOne(JOBS_JOB_PERKS, JOBS_JOB_PERKS.JOB_PERK_ID.eq(perkId).and(JOBS_JOB_PERKS.JOB_ID.eq(jobId))) ?: db.newRecord(JOBS_JOB_PERKS)
        record.jobPerkId = perkId
        record.jobId = jobId
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
        positionType: String,
        hasStar: Boolean,
        isDeleted: Boolean,
        approvedAt: OffsetDateTime,
        poster: String
    ) {
        val record = db.fetchOne(JOBS, JOBS.JOB_ID.eq(jobId)) ?: db.newRecord(JOBS)
        record.jobId = jobId
        record.companyId = companyId
        record.title = title
        record.monthlySalary = monthlySalary
        record.equity = equity
        record.description = description
        record.positionType = positionType
        record.hasStar = hasStar
        record.isDeleted = isDeleted
        record.approvedAt = approvedAt
        record.poster = poster
        if (record != record.original()) {
            record.dateModified = now()
        }
        record.store()
    }
}
