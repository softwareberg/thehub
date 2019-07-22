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

object DatabaseSetupOperations {

    private val dateCreated = OffsetDateTime.parse("2007-12-03T10:15:30Z")
    private val dateModified = OffsetDateTime.parse("2007-12-03T10:15:30Z")

    private const val monthlySalary = "3000$"
    private const val equity = "50-50"
    private const val positionType = "full-time"
    private const val location = "Warszawa"
    private const val domain = "pl"
    private const val companyId = "nice-company-id"
    private const val keyword = "java"
    private const val jobId = "nice-job-id"

    fun deleteAll(): (DSLContext) -> Unit = { db ->
        db.delete(JOBS_JOB_KEYWORDS).execute()
        db.delete(JOBS_JOB_PERKS).execute()
        db.delete(JOB_KEYWORDS).execute()
        db.delete(JOB_PERKS).execute()
        db.delete(JOBS).execute()
        db.delete(POSITIONS_TYPES).execute()
        db.delete(EQUITIES).execute()
        db.delete(MONTHLY_SALARIES).execute()
        db.delete(COMPANIES).execute()
        db.delete(DOMAINS).execute()
        db.delete(LOCATIONS).execute()
    }

    fun insertMonthlySalary(
        monthlySalary: String = this.monthlySalary,
        dateCreated: OffsetDateTime = this.dateCreated,
        dateModified: OffsetDateTime = this.dateModified
    ): (DSLContext) -> Unit = { db ->
        val record = db.newRecord(MONTHLY_SALARIES)
        record.monthlySalary = monthlySalary
        record.dateCreated = dateCreated
        record.dateModified = dateModified
        record.store()
    }

    fun insertEquity(
        equity: String = this.equity,
        dateCreated: OffsetDateTime = this.dateCreated,
        dateModified: OffsetDateTime = this.dateModified
    ): (DSLContext) -> Unit = { db ->
        val record = db.newRecord(EQUITIES)
        record.equity = equity
        record.dateCreated = dateCreated
        record.dateModified = dateModified
        record.store()
    }

    fun insertPositionType(
        positionType: String = this.positionType,
        dateCreated: OffsetDateTime = this.dateCreated,
        dateModified: OffsetDateTime = this.dateModified
    ): (DSLContext) -> Unit = { db ->
        val record = db.newRecord(POSITIONS_TYPES)
        record.positionType = positionType
        record.dateCreated = dateCreated
        record.dateModified = dateModified
        record.store()
    }

    fun insertLocation(
        location: String = this.location,
        dateCreated: OffsetDateTime = this.dateCreated,
        dateModified: OffsetDateTime = this.dateModified
    ): (DSLContext) -> Unit = { db ->
        val record = db.newRecord(LOCATIONS)
        record.location = location
        record.dateCreated = dateCreated
        record.dateModified = dateModified
        record.store()
    }

    fun insertDomain(
        domain: String = this.domain,
        dateCreated: OffsetDateTime = this.dateCreated,
        dateModified: OffsetDateTime = this.dateModified
    ): (DSLContext) -> Unit = { db ->
        val record = db.newRecord(DOMAINS)
        record.domain = domain
        record.dateCreated = dateCreated
        record.dateModified = dateModified
        record.store()
    }

    @SuppressWarnings("LongParameterList")
    fun insertCompany(
        companyId: String = this.companyId,
        name: String = "Nice Company",
        logo: String = "logo.png",
        domain: String = this.domain,
        location: String = this.location,
        dateCreated: OffsetDateTime = this.dateCreated,
        dateModified: OffsetDateTime = this.dateModified
    ): (DSLContext) -> Unit = { db ->
        val record = db.newRecord(COMPANIES)
        record.companyId = companyId
        record.name = name
        record.logo = logo
        record.domain = domain
        record.location = location
        record.dateCreated = dateCreated
        record.dateModified = dateModified
        record.store()
    }

    fun insertKeyword(
        keyword: String = this.keyword,
        dateCreated: OffsetDateTime = this.dateCreated,
        dateModified: OffsetDateTime = this.dateModified
    ): (DSLContext) -> Unit = { db ->
        val record = db.newRecord(JOB_KEYWORDS)
        record.keyword = keyword
        record.dateCreated = dateCreated
        record.dateModified = dateModified
        record.store()
    }

    fun insertJobKeyword(
        keyword: String = this.keyword,
        jobId: String = this.jobId,
        dateCreated: OffsetDateTime = this.dateCreated,
        dateModified: OffsetDateTime = this.dateModified
    ): (DSLContext) -> Unit = { db ->
        val record = db.newRecord(JOBS_JOB_KEYWORDS)
        record.keyword = keyword
        record.jobId = jobId
        record.dateCreated = dateCreated
        record.dateModified = dateModified
        record.store()
    }

    @SuppressWarnings("LongParameterList")
    fun insertJob(
        jobId: String = this.jobId,
        companyId: String = this.companyId,
        monthlySalary: String = this.monthlySalary,
        equity: String = this.equity,
        positionType: String = this.positionType,
        title: String = "Software Developer",
        description: String = "nice job",
        hasStar: Boolean = false,
        isDeleted: Boolean = false,
        dateCreated: OffsetDateTime = this.dateCreated,
        dateModified: OffsetDateTime = this.dateModified
    ): (DSLContext) -> Unit = { db ->
        val record = db.newRecord(JOBS)
        record.jobId = jobId
        record.companyId = companyId
        record.title = title
        record.monthlySalary = monthlySalary
        record.equity = equity
        record.description = description
        record.positionType = positionType
        record.hasStar = hasStar
        record.isDeleted = isDeleted
        record.dateCreated = dateCreated
        record.dateModified = dateModified
        record.store()
    }
}
