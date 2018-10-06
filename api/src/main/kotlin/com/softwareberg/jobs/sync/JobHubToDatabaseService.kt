package com.softwareberg.jobs.sync

import com.softwareberg.Database
import com.softwareberg.params

class JobHubToDatabaseService(private val database: Database) {

    fun upserJob(job: Job) {
        database.transaction {
            upsertDomain(job.host)
            upsertLocation(job.locationLabel)
            upsertPositionType(job.positionType)
            job.keywords.forEach(this::upsertJobKeyword)
            job.perks.forEach { perk -> upsertPerk(perk.key, perk.description) }
            upserCompany(job.company.key, job.company.name, job.company.logo.filename, job.host, job.locationLabel)
            job.equity?.also(this::upserEquity)
            job.monthlySalary?.also(this::upserMonthlySalary)
            upserJob(job.key, job.company.key, job.title, job.description, job.positionType, job.monthlySalary, job.equity)
            job.keywords.forEach { keyword -> upserJoinJobWithKeyword(job.key, keyword) }
            job.perks.forEach { perk -> upserJoinJobWithPerk(job.key, perk.key) }
        }
    }

    private fun upsertLocation(location: String) {
        val sql = """
        INSERT INTO locations (location) VALUES
          (:location)
        ON CONFLICT DO NOTHING
        """
        database.insert(sql.params("location" to location))
    }

    private fun upsertDomain(domain: String) {
        val sql = """
        INSERT INTO domains (domain) VALUES
          (:domain)
        ON CONFLICT DO NOTHING
        """
        database.insert(sql.params("domain" to domain))
    }

    private fun upserEquity(equity: String) {
        val sql = """
        INSERT INTO equities (equity) VALUES
          (:equity)
        ON CONFLICT DO NOTHING
        """
        database.insert(sql.params("equity" to equity))
    }

    private fun upserMonthlySalary(monthlySalary: String) {
        val sql = """
        INSERT INTO monthly_salaries (monthly_salary) VALUES
          (:monthly_salary)
        ON CONFLICT DO NOTHING
        """
        database.insert(sql.params("monthly_salary" to monthlySalary))
    }

    private fun upserCompany(key: String, name: String, logo: String, domain: String, location: String) {
        val sql = """
        INSERT INTO companies (company_id, name, logo, domain, location) VALUES
          (:company_id, :name, :logo, :domain, :location)
        ON CONFLICT (company_id)
          DO UPDATE SET name = EXCLUDED.name, logo = EXCLUDED.logo, domain = EXCLUDED.domain, location = EXCLUDED.location, date_modified = now()
        """
        database.insert(sql.params("company_id" to key, "name" to name, "logo" to logo, "domain" to domain, "location" to location))
    }

    private fun upsertPositionType(positionType: String) {
        val sql = """
        INSERT INTO positions_types (position_type) VALUES
          (:position_type)
        ON CONFLICT DO NOTHING
        """
        database.insert(sql.params("position_type" to positionType))
    }

    private fun upsertJobKeyword(keyword: String) {
        val sql = """
        INSERT INTO job_keywords (keyword) VALUES
          (:keyword)
        ON CONFLICT DO NOTHING
        """
        database.insert(sql.params("keyword" to keyword))
    }

    private fun upsertPerk(key: String, description: String) {
        val sql = """
        INSERT INTO job_perks (job_perk_id, description) VALUES
          (:job_perk_id, :description)
        ON CONFLICT DO NOTHING
        """
        database.insert(sql.params("job_perk_id" to key, "description" to description))
    }

    private fun upserJob(
        key: String,
        companyId: String,
        title: String,
        description: String,
        positionType: String,
        monthlySalary: String?,
        equity: String?
    ) {
        val sql = """
        INSERT INTO jobs (job_id, company_id, title, monthly_salary, equity, description, position_type) VALUES
          (:job_id, :company_id, :title, :monthly_salary, :equity, :description, :position_type)
        ON CONFLICT (job_id)
          DO UPDATE SET company_id = EXCLUDED.company_id, title = EXCLUDED.title, monthly_salary = EXCLUDED.monthly_salary, equity = EXCLUDED.equity, description = EXCLUDED.description, position_type = EXCLUDED.position_type, date_modified = now()
        """
        val statement = sql.params(
            "monthly_salary" to monthlySalary,
            "job_id" to key,
            "company_id" to companyId,
            "title" to title,
            "equity" to equity,
            "description" to description,
            "position_type" to positionType
        )
        database.insert(statement)
    }

    private fun upserJoinJobWithPerk(jobKey: String, perkKey: String) {
        val sql = """
        INSERT INTO jobs_job_perks (job_perk_id, job_id) VALUES
          (:job_perk_id, :job_id)
        ON CONFLICT DO NOTHING
        """
        database.insert(sql.params("job_perk_id" to perkKey, "job_id" to jobKey))
    }

    private fun upserJoinJobWithKeyword(jobKey: String, keyword: String) {
        val sql = """
        INSERT INTO jobs_job_keywords (keyword, job_id) VALUES
          (:keyword, :job_id)
        ON CONFLICT DO NOTHING
        """
        database.insert(sql.params("keyword" to keyword, "job_id" to jobKey))
    }
}
