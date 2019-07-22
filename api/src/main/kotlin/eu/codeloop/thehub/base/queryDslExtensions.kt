package eu.codeloop.thehub.base

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.StringPath

fun StringPath.equalsIgnoreCaseOrNull(value: String?): BooleanExpression? = if (value != null) this.equalsIgnoreCase(value) else null
fun StringPath.containsIgnoreCaseOrNull(value: String?): BooleanExpression? = if (value != null) this.containsIgnoreCase(value) else null
