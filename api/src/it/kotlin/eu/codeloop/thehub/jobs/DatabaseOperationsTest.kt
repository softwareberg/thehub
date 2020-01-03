package eu.codeloop.thehub.jobs

import eu.codeloop.thehub.base.DatabaseOperations
import eu.codeloop.thehub.base.DatabaseSetup
import eu.codeloop.thehub.base.DatabaseSetupOperations.deleteAll
import eu.codeloop.thehub.base.DatabaseSetupOperations.insertCompany
import eu.codeloop.thehub.base.DatabaseSetupOperations.insertLocation
import eu.codeloop.thehub.base.DatabaseSetupOperations.insertSalary
import eu.codeloop.thehub.jooq.Tables.COMPANIES
import eu.codeloop.thehub.jooq.Tables.MONTHLY_SALARIES
import org.assertj.core.api.Assertions.assertThat
import org.jooq.DSLContext
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.time.OffsetDateTime

@RunWith(SpringRunner::class)
@SpringBootTest
class DatabaseOperationsTest {

    @Autowired
    private lateinit var db: DSLContext

    @Autowired
    private lateinit var databaseSetup: DatabaseSetup

    private val databaseOperations: DatabaseOperations by lazy {
        DatabaseOperations(db)
    }

    @Test
    fun `it should not modify salary when value is the same`() {
        // given
        val date = OffsetDateTime.parse("2007-12-03T10:15:30Z")
        databaseSetup.prepareDatabase(
            deleteAll(),
            insertSalary(monthlySalary = "competitive", dateCreated = date, dateModified = date)
        )

        // when
        databaseOperations.upsertSalary(monthlySalary = "competitive")

        // then
        val keyword = db.fetchOne(MONTHLY_SALARIES, MONTHLY_SALARIES.MONTHLY_SALARY.eq("competitive"))
        assertThat(keyword.monthlySalary).isEqualTo("competitive")
        assertThat(keyword.dateCreated.toInstant()).isEqualTo(date.toInstant())
        assertThat(keyword.dateModified.toInstant()).isEqualTo(date.toInstant())
    }

    @Test
    fun `it should not modify company when old values are the same as new ones`() {
        // given
        val date = OffsetDateTime.parse("2007-12-03T10:15:30Z")
        databaseSetup.prepareDatabase(
            deleteAll(),
            insertLocation(location = "New York"),
            insertCompany(companyId = "foo", name = "Foobar Inc.", location = "New York", logo = "logo.png")
        )

        // when
        databaseOperations.upsertCompany(companyId = "foo", name = "Foobar Inc.", location = "New York", logo = "logo.png")

        // then
        val company = db.fetchOne(COMPANIES, COMPANIES.COMPANY_ID.eq("foo"))
        assertThat(company.companyId).isEqualTo("foo")
        assertThat(company.name).isEqualTo("Foobar Inc.")
        assertThat(company.dateCreated.toInstant()).isEqualTo(date.toInstant())
        assertThat(company.dateModified.toInstant()).isEqualTo(date.toInstant())
    }

    @Test
    fun `it should modify company when old values are not the same as new ones`() {
        // given
        val date = OffsetDateTime.parse("2007-12-03T10:15:30Z")
        databaseSetup.prepareDatabase(
            deleteAll(),
            insertLocation(location = "New York"),
            insertCompany(companyId = "foo", name = "Foo Inc.", location = "New York")
        )

        // when
        databaseOperations.upsertCompany(companyId = "foo", name = "Foobar Inc.", location = "New York", logo = "logo.png")

        // then
        val company = db.fetchOne(COMPANIES, COMPANIES.COMPANY_ID.eq("foo"))
        assertThat(company.companyId).isEqualTo("foo")
        assertThat(company.name).isEqualTo("Foobar Inc.")
        assertThat(company.dateCreated.toInstant()).isEqualTo(date.toInstant())
        assertThat(company.dateModified.toInstant()).isNotEqualTo(date.toInstant())
    }
}
