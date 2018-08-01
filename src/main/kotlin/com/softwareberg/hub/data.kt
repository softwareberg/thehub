package com.softwareberg.hub

data class CompaniesWrapper(val companies: Companies)
data class Companies(val docs: List<Company>, val pages: Int)
data class Company(val key: String, val name: String)
data class CompanyWithPositions(val key: String, val name: String, val positions: List<String>)
