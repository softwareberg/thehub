package com.softwareberg.thehub.jobs

import com.softwareberg.thehub.base.DatabaseSetup
import com.softwareberg.thehub.base.DatabaseSetupOperations.deleteAll
import com.softwareberg.thehub.base.DatabaseSetupOperations.insertCompany
import com.softwareberg.thehub.base.DatabaseSetupOperations.insertDomain
import com.softwareberg.thehub.base.DatabaseSetupOperations.insertKeyword
import com.softwareberg.thehub.base.DatabaseSetupOperations.insertLocation
import com.softwareberg.thehub.jooq.Tables.COMPANIES
import com.softwareberg.thehub.jooq.Tables.JOB_KEYWORDS
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

    @Autowired
    private lateinit var databaseOperations: DatabaseOperations

    @Test
    fun `it should not modify keyword when value is the same`() {
        // given
        val date = OffsetDateTime.parse("2007-12-03T10:15:30Z")
        databaseSetup.prepareDatabase(
            deleteAll(),
            insertKeyword(keyword = "java", dateCreated = date, dateModified = date)
        )

        // when
        databaseOperations.upsertKeyword(keyword = "java")

        // then
        val keyword = db.fetchOne(JOB_KEYWORDS, JOB_KEYWORDS.KEYWORD.eq("java"))
        assertThat(keyword.keyword).isEqualTo("java")
        assertThat(keyword.dateCreated.toInstant()).isEqualTo(date.toInstant())
        assertThat(keyword.dateModified.toInstant()).isEqualTo(date.toInstant())
    }

    @Test
    fun `it should not modify company when old values are the same as new ones`() {
        // given
        val date = OffsetDateTime.parse("2007-12-03T10:15:30Z")
        databaseSetup.prepareDatabase(
            deleteAll(),
            insertDomain(domain = "USA"),
            insertLocation(location = "New York"),
            insertCompany(companyId = "foo", name = "Foobar Inc.", domain = "USA", location = "New York", logo = "logo.png")
        )

        // when
        databaseOperations.upsertCompany(companyId = "foo", name = "Foobar Inc.", domain = "USA", location = "New York", logo = "logo.png")

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
            insertDomain(domain = "USA"),
            insertLocation(location = "New York"),
            insertCompany(companyId = "foo", name = "Foo Inc.", domain = "USA", location = "New York")
        )

        // when
        databaseOperations.upsertCompany(companyId = "foo", name = "Foobar Inc.", domain = "USA", location = "New York", logo = "logo.png")

        // then
        val company = db.fetchOne(COMPANIES, COMPANIES.COMPANY_ID.eq("foo"))
        assertThat(company.companyId).isEqualTo("foo")
        assertThat(company.name).isEqualTo("Foobar Inc.")
        assertThat(company.dateCreated.toInstant()).isEqualTo(date.toInstant())
        assertThat(company.dateModified.toInstant()).isNotEqualTo(date.toInstant())
    }
}
