package com.softwareberg.thehub.base

import org.jooq.DSLContext
import org.springframework.stereotype.Component

@Component
class DatabaseSetup(private val db: DSLContext) {

    fun prepareDatabase(vararg operations: (DSLContext) -> Unit) {
        operations.forEach { operation -> operation(db) }
    }
}
