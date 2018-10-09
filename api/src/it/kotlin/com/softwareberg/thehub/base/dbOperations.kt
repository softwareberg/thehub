package com.softwareberg.thehub.base

import com.softwareberg.thehub.jooq.Tables.COMPANIES
import com.softwareberg.thehub.jooq.Tables.DOMAINS
import com.softwareberg.thehub.jooq.Tables.EQUITIES
import com.softwareberg.thehub.jooq.Tables.JOBS
import com.softwareberg.thehub.jooq.Tables.JOBS_JOB_KEYWORDS
import com.softwareberg.thehub.jooq.Tables.JOBS_JOB_PERKS
import com.softwareberg.thehub.jooq.Tables.JOB_KEYWORDS
import com.softwareberg.thehub.jooq.Tables.JOB_PERKS
import com.softwareberg.thehub.jooq.Tables.LOCATIONS
import com.softwareberg.thehub.jooq.Tables.MONTHLY_SALARIES
import com.softwareberg.thehub.jooq.Tables.POSITIONS_TYPES
import org.jooq.DSLContext
import java.time.OffsetDateTime

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
    monthlySalary: String = "3000$",
    dateCreated: OffsetDateTime? = null,
    dateModified: OffsetDateTime? = null
): (DSLContext) -> Unit = { db ->
    val monthlySalaryRecord = db.newRecord(MONTHLY_SALARIES)
    monthlySalaryRecord.monthlySalary = monthlySalary
    if (dateCreated != null) {
        monthlySalaryRecord.dateCreated = dateCreated
    }
    if (dateModified != null) {
        monthlySalaryRecord.dateModified = dateModified
    }
    monthlySalaryRecord.store()
}

fun insertEquity(
    equity: String = "50-50",
    dateCreated: OffsetDateTime? = null,
    dateModified: OffsetDateTime? = null
): (DSLContext) -> Unit = { db ->
    val equityRecord = db.newRecord(EQUITIES)
    equityRecord.equity = equity
    if (dateCreated != null) {
        equityRecord.dateCreated = dateCreated
    }
    if (dateModified != null) {
        equityRecord.dateModified = dateModified
    }
    equityRecord.store()
}

fun insertPositionType(
    positionType: String = "full-time",
    dateCreated: OffsetDateTime? = null,
    dateModified: OffsetDateTime? = null
): (DSLContext) -> Unit = { db ->
    val positionTypeRecord = db.newRecord(POSITIONS_TYPES)
    positionTypeRecord.positionType = positionType
    if (dateCreated != null) {
        positionTypeRecord.dateCreated = dateCreated
    }
    if (dateModified != null) {
        positionTypeRecord.dateModified = dateModified
    }
    positionTypeRecord.store()
}

fun insertLocation(
    location: String = "Warszawa",
    dateCreated: OffsetDateTime? = null,
    dateModified: OffsetDateTime? = null
): (DSLContext) -> Unit = { db ->
    val locationRecord = db.newRecord(LOCATIONS)
    locationRecord.location = location
    if (dateCreated != null) {
        locationRecord.dateCreated = dateCreated
    }
    if (dateModified != null) {
        locationRecord.dateModified = dateModified
    }
    locationRecord.store()
}

fun insertDomain(
    domain: String = "pl",
    dateCreated: OffsetDateTime? = null,
    dateModified: OffsetDateTime? = null
): (DSLContext) -> Unit = { db ->
    val domainRecord = db.newRecord(DOMAINS)
    domainRecord.domain = domain
    if (dateCreated != null) {
        domainRecord.dateCreated = dateCreated
    }
    if (dateModified != null) {
        domainRecord.dateModified = dateModified
    }
    domainRecord.store()
}

fun insertCompany(
    companyId: String = "nice-company-id",
    name: String = "Nice Company",
    logo: String = "logo.png",
    domain: String = "pl",
    location: String = "Warszawa",
    dateCreated: OffsetDateTime? = null,
    dateModified: OffsetDateTime? = null
): (DSLContext) -> Unit = { db ->
    val company = db.newRecord(COMPANIES)
    company.companyId = companyId
    company.name = name
    company.logo = logo
    company.domain = domain
    company.location = location
    if (dateCreated != null) {
        company.dateCreated = dateCreated
    }
    if (dateModified != null) {
        company.dateModified = dateModified
    }
    company.store()
}

fun insertKeyword(
    keyword: String = "java",
    dateCreated: OffsetDateTime? = null,
    dateModified: OffsetDateTime? = null
): (DSLContext) -> Unit = { db ->
    val keywordRecord = db.newRecord(JOB_KEYWORDS)
    keywordRecord.keyword = keyword
    if (dateCreated != null) {
        keywordRecord.dateCreated = dateCreated
    }
    if (dateModified != null) {
        keywordRecord.dateModified = dateModified
    }
    keywordRecord.store()
}

fun insertJobKeyword(
    keyword: String = "java",
    jobId: String = "nice-job-id",
    dateCreated: OffsetDateTime? = null,
    dateModified: OffsetDateTime? = null
): (DSLContext) -> Unit = { db ->
    val keywordRecord = db.newRecord(JOBS_JOB_KEYWORDS)
    keywordRecord.keyword = keyword
    keywordRecord.jobId = jobId
    if (dateCreated != null) {
        keywordRecord.dateCreated = dateCreated
    }
    if (dateModified != null) {
        keywordRecord.dateModified = dateModified
    }
    keywordRecord.store()
}

fun insertJob(
    jobId: String = "nice-job-id",
    companyId: String = "nice-company-id",
    title: String = "Software Developer",
    monthlySalary: String = "3000$",
    equity: String = "50-50",
    description: String = "nice job",
    positionType: String = "full-time",
    dateCreated: OffsetDateTime? = null,
    dateModified: OffsetDateTime? = null,
    hasStar: Boolean = false,
    isDeleted: Boolean = false
): (DSLContext) -> Unit = { db ->
    val job = db.newRecord(JOBS)
    job.jobId = jobId
    job.companyId = companyId
    job.title = title
    job.monthlySalary = monthlySalary
    job.equity = equity
    job.description = description
    job.positionType = positionType
    job.hasStar = hasStar
    job.isDeleted = isDeleted
    if (dateCreated != null) {
        job.dateCreated = dateCreated
    }
    if (dateModified != null) {
        job.dateModified = dateModified
    }
    job.store()
}
