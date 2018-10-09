package com.softwareberg.thehub.base

import com.querydsl.core.types.ConstantImpl
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.StringPath

fun StringPath.equalsIgnoreCaseOrNull(value: String?): BooleanExpression? = if (value != null) this.equalsIgnoreCase(value) else null
fun StringPath.containsIgnoreCaseOrNull(value: String?): BooleanExpression? = if (value != null) this.containsIgnoreCase(value) else null
fun StringPath.similarOrNull(value: String?): BooleanExpression? {
    return if (value != null) {
        Expressions.booleanOperation(SimilarOperator, this, ConstantImpl.create(value))
    } else {
        null
    }
}
