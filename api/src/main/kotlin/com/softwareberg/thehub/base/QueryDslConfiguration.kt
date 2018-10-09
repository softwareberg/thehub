package com.softwareberg.thehub.base

import com.querydsl.sql.SQLQueryFactory
import com.querydsl.sql.spring.SpringConnectionProvider
import com.querydsl.sql.spring.SpringExceptionTranslator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource

@Configuration
class QueryDslConfiguration {

    @Primary
    @Bean
    fun queryFactory(dataSource: DataSource): SQLQueryFactory {
        val templates = PostgreSQLTemplatesCustom()
        val configuration = com.querydsl.sql.Configuration(templates)
        configuration.setExceptionTranslator(SpringExceptionTranslator())
        return SQLQueryFactory(configuration, SpringConnectionProvider(dataSource))
    }
}
