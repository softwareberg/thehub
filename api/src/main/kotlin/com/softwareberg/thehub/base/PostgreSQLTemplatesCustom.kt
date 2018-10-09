package com.softwareberg.thehub.base

import com.querydsl.sql.PostgreSQLTemplates

class PostgreSQLTemplatesCustom : PostgreSQLTemplates() {
    init {
        add(SimilarOperator, "{0} similar {1}")
    }
}
