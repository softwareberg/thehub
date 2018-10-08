package com.softwareberg.thehub.jobs

data class CompanyDto(val companyId: String, val name: String, val jobs: List<JobDto> = emptyList())
