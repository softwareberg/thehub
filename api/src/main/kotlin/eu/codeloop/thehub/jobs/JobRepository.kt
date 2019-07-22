package eu.codeloop.thehub.jobs

import eu.codeloop.thehub.jobs.model.JobEntity
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.PagingAndSortingRepository

interface JobRepository : PagingAndSortingRepository<JobEntity, String>, QuerydslPredicateExecutor<JobEntity>
