package com.softwareberg.thehub.jobs

import com.softwareberg.thehub.jobs.model.CompanyEntity
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.PagingAndSortingRepository

interface CompanyRepository : PagingAndSortingRepository<CompanyEntity, String>, QuerydslPredicateExecutor<CompanyEntity>
