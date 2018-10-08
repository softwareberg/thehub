package com.softwareberg.thehub.jobs

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.UUID

interface JobsRepository : PagingAndSortingRepository<JobEntity, UUID>, QuerydslPredicateExecutor<JobEntity>
