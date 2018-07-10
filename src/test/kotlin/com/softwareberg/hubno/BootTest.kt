package com.softwareberg.hubno

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class BootTest {

    @Test
    fun `it should filter companies by keywords in positions`() {
        // given
        val boot = Boot()
        val keywords = listOf("eng", "dev")
        val companies = listOf(
            companyWithPositions("engg"),
            companyWithPositions("aaaeENG"),
            companyWithPositions("dev"),
            companyWithPositions("dev", "eng"),
            companyWithPositions("dev", "eng", "other"),
            companyWithPositions("other"),
            companyWithPositions(""),
            companyWithPositions("", "dev"),
            companyWithPositions()
        )

        // when
        val filtered = boot.filter(companies, keywords)

        // then
        assertThat(filtered).hasSize(6)
        assertThat(filtered).contains(companyWithPositions("aaaeENG"))
        assertThat(filtered).doesNotContain(companyWithPositions(""))
    }

    private fun companyWithPositions(vararg positions: String): CompanyWithPositions {
        return CompanyWithPositions("foo", "foo", positions.toList())
    }
}
