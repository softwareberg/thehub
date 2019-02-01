package com.softwareberg.thehub.jobs

import com.softwareberg.thehub.jobs.model.JobEntity
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.PagingAndSortingRepository

interface JobRepository : PagingAndSortingRepository<JobEntity, String>, QuerydslPredicateExecutor<JobEntity>
