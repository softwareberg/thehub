package com.softwareberg.thehub.jobs

import org.springframework.data.repository.PagingAndSortingRepository
import java.util.UUID

interface JobsRepository : PagingAndSortingRepository<JobEntity, UUID>
