package eu.codeloop.thehub.base

import eu.codeloop.thehub.jooq.Tables.COMPANIES
import eu.codeloop.thehub.jooq.Tables.EQUITIES
import eu.codeloop.thehub.jooq.Tables.JOBS
import eu.codeloop.thehub.jooq.Tables.LOCATIONS
import eu.codeloop.thehub.jooq.Tables.MONTHLY_SALARIES
import org.jooq.DSLContext
import java.time.OffsetDateTime

object DatabaseSetupOperations {

    private val dateCreated = OffsetDateTime.parse("2007-12-03T10:15:30Z")
    private val dateModified = OffsetDateTime.parse("2007-12-03T10:15:30Z")

    private const val monthlySalary = "3000$"
    private const val location = "Warszawa"
    private const val domain = "pl"
    private const val companyId = "nice-company-id"

    fun deleteAll(): (DSLContext) -> Unit = { db ->
        db.delete(JOBS).execute()
        db.delete(EQUITIES).execute()
        db.delete(MONTHLY_SALARIES).execute()
        db.delete(COMPANIES).execute()
        db.delete(LOCATIONS).execute()
    }

    fun insertSalary(
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

    @SuppressWarnings("LongParameterList")
    fun insertCompany(
        companyId: String = this.companyId,
        name: String = "Nice Company",
        logo: String = "logo.png",
        location: String = this.location,
        dateCreated: OffsetDateTime = this.dateCreated,
        dateModified: OffsetDateTime = this.dateModified
    ): (DSLContext) -> Unit = { db ->
        val record = db.newRecord(COMPANIES)
        record.companyId = companyId
        record.name = name
        record.logo = logo
        record.location = location
        record.dateCreated = dateCreated
        record.dateModified = dateModified
        record.store()
    }
}
