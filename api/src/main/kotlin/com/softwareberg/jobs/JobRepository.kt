package com.softwareberg.jobs

import com.softwareberg.base.web.errors.NotFoundException
import com.softwareberg.database.Tables.COMPANIES
import com.softwareberg.database.Tables.JOBS
import com.softwareberg.database.Tables.JOBS_JOB_KEYWORDS
import com.softwareberg.database.tables.records.JobsRecord
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.RecordMapper
import java.util.ArrayList

class JobRepository(private val db: DSLContext) {

    private data class JobRow(val jobId: String, val title: String, val description: String, val hasStar: Boolean, val keyword: String?, val domain: String)

    private val jobMapper: RecordMapper<Record, JobRow> = RecordMapper {
        JobRow(
            it.get(JOBS.JOB_ID),
            it.get(JOBS.TITLE),
            it.get(JOBS.DESCRIPTION),
            it.get(JOBS.HAS_STAR),
            it.get(JOBS_JOB_KEYWORDS.KEYWORD),
            it.get(COMPANIES.DOMAIN)
        )
    }

    fun findAll(page: Int, pageSize: Int): List<Job> {
        val offset = offset(page, pageSize)

        val j = JOBS.`as`("j")

        val nested = db.selectFrom(JOBS)
            .orderBy(JOBS.JOB_ID)
            .limit(pageSize)
            .offset(offset)
            .asTable("j")

        val jobs = db.select(j.JOB_ID, j.TITLE, j.DESCRIPTION, j.HAS_STAR, COMPANIES.DOMAIN, JOBS_JOB_KEYWORDS.KEYWORD)
            .from(nested)
            .innerJoin(COMPANIES).on(COMPANIES.COMPANY_ID.eq(j.COMPANY_ID))
            .leftJoin(JOBS_JOB_KEYWORDS).on(JOBS_JOB_KEYWORDS.JOB_ID.eq(j.JOB_ID))
            .orderBy(j.JOB_ID)
            .fetch(jobMapper)

        return mapToJob(jobs)
    }

    fun searchByKeyword(keyword: String): List<Job> {
        val s = JOBS_JOB_KEYWORDS.`as`("s")

        val nested = db.selectFrom(JOBS_JOB_KEYWORDS)
            .where(JOBS_JOB_KEYWORDS.KEYWORD.likeIgnoreCase(keyword))
            .asTable("s")

        val jobsRows = db.select(JOBS.JOB_ID, JOBS.TITLE, JOBS.DESCRIPTION, JOBS.HAS_STAR, COMPANIES.DOMAIN, JOBS_JOB_KEYWORDS.KEYWORD)
            .from(nested)
            .innerJoin(JOBS).on(JOBS.JOB_ID.eq(s.JOB_ID))
            .innerJoin(COMPANIES).on(COMPANIES.COMPANY_ID.eq(JOBS.COMPANY_ID))
            .innerJoin(JOBS_JOB_KEYWORDS).on(JOBS_JOB_KEYWORDS.JOB_ID.eq(JOBS.JOB_ID))
            .orderBy(JOBS.JOB_ID)
            .fetch(jobMapper)

        return mapToJob(jobsRows)
    }

    fun findWithStars(): List<Job> {
        val jobs = db.select(JOBS.JOB_ID, JOBS.TITLE, JOBS.DESCRIPTION, JOBS.HAS_STAR, COMPANIES.DOMAIN, JOBS_JOB_KEYWORDS.KEYWORD)
            .from(JOBS)
            .innerJoin(COMPANIES).on(COMPANIES.COMPANY_ID.eq(JOBS.COMPANY_ID))
            .innerJoin(JOBS_JOB_KEYWORDS).on(JOBS_JOB_KEYWORDS.JOB_ID.eq(JOBS.JOB_ID))
            .orderBy(JOBS.JOB_ID)
            .fetch(jobMapper)

        return mapToJob(jobs)
    }

    fun setStar(jobId: String, hasStar: Boolean) {
        val job = getById(jobId)
        job.hasStar = hasStar
        job.store()
    }

    private fun getById(jobId: String): JobsRecord = findById(jobId) ?: throw NotFoundException("Job with id=$jobId not found")

    private fun findById(jobId: String): JobsRecord? = db.fetchOne(JOBS, JOBS.JOB_ID.eq(jobId))

    fun notExists(jobId: String): Boolean {
        return !exists(jobId)
    }

    private fun exists(jobId: String): Boolean {
        return db.fetchOne(JOBS, JOBS.JOB_ID.eq(jobId)) != null
    }

    fun delete(jobId: String) {
        val job = findById(jobId)
        if (job != null) {
            job.isDeleted = true
            job.store()
        }
    }

    private fun mapToJob(jobsRows: List<JobRow>): List<Job> {
        val jobById = LinkedHashMap<String, Job>()
        val keywordsByJobId = LinkedHashMap<String, MutableList<String>>()
        jobsRows.forEach { jobRow ->
            val keywords = keywordsByJobId.getOrDefault(jobRow.jobId, ArrayList())
            if (jobRow.keyword != null) {
                keywords.add(jobRow.keyword)
            }
            keywordsByJobId[jobRow.jobId] = keywords
            val href = "https://${jobRow.domain}/jobs/${jobRow.jobId}"
            val job = jobById[jobRow.jobId] ?: Job(jobRow.jobId, jobRow.title, jobRow.description, jobRow.hasStar, keywords, href)
            jobById[jobRow.jobId] = job
        }
        return jobById.values.toList()
    }

    private fun offset(page: Int, pageSize: Int) = if (page < 1) 0 else (page - 1) * pageSize
}
