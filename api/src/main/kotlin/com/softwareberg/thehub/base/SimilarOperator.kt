package com.softwareberg.thehub.base

import com.querydsl.core.types.Operator

object SimilarOperator : Operator {
    override fun getType(): Class<*> = java.lang.Boolean::class.java
    override fun name(): String = "SIMILAR"
}
