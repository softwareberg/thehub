package com.softwareberg.thehub.jobs

import org.springframework.data.repository.PagingAndSortingRepository
import java.util.UUID

interface CompaniesRepository : PagingAndSortingRepository<CompanyEntity, UUID>
