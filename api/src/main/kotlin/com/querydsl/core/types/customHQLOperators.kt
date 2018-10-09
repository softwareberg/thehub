package com.querydsl.core.types

import com.querydsl.jpa.HQLTemplates

fun addSupport(op: Operator, pattern: String) {
    HQLTemplates.DEFAULT.add(op, pattern)
}
