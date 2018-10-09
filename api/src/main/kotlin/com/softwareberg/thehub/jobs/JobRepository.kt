package com.softwareberg.thehub.jobs

import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.UUID

interface JobRepository : PagingAndSortingRepository<JobEntity, UUID>, QuerydslPredicateExecutor<JobEntity>
