package eu.codeloop.thehub.jobs

import eu.codeloop.thehub.jobs.model.CompanyEntity
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.PagingAndSortingRepository

interface CompanyRepository : PagingAndSortingRepository<CompanyEntity, String>, QuerydslPredicateExecutor<CompanyEntity>
