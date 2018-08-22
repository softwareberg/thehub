package com.softwareberg.companies

data class CompanyWithPositions(
    val key: String,
    val name: String,
    val positions: List<String>
)
