package com.softwareberg.thehub.base

import com.querydsl.core.types.Operator
import com.querydsl.core.types.addSupport
import com.querydsl.jpa.HQLTemplates

object SimilarOperator : Operator {

    override fun getType(): Class<*> = java.lang.Boolean::class.java
    override fun name(): String = "SIMILAR"

    init {
        addSupport(this, "{0} similar {1} escape '" + HQLTemplates.DEFAULT.escapeChar + "'")
    }
}
