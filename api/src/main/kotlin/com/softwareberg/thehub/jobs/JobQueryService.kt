package com.softwareberg.thehub.jobs

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import com.softwareberg.thehub.base.containsIgnoreCaseOrNull
import com.softwareberg.thehub.base.equalsIgnoreCaseOrNull
import com.softwareberg.thehub.jobs.QJobEntity.jobEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service


@Service
class JobQueryService(private val jobsRepository: JobRepository) {

    fun findAll(title: String?, keyword: String?, q: String?, pageable: Pageable): Page<JobEntity> {
        val predicate = createPredicate(title, keyword, q)
        return jobsRepository.findAll(predicate, pageable)
    }

    private fun createPredicate(title: String?, keyword: String?, q: String?): Predicate {
        val containsTitle = containsInTitle(title)
        val containsKeyword = hasKeyword(keyword)
        val search = searchInAllPredicate(q)
        return BooleanBuilder().and(containsTitle).and(containsKeyword).and(search)
    }

    private fun searchInAllPredicate(q: String?): Predicate = BooleanBuilder().or(containsInTitle(q)).or(containsInDescription(q)).or(hasKeyword(q))
    private fun containsInDescription(description: String?): Predicate? = jobEntity.description.containsIgnoreCaseOrNull(description)
    private fun containsInTitle(title: String?): Predicate? = jobEntity.title.containsIgnoreCaseOrNull(title)
    private fun hasKeyword(keyword: String?): Predicate? = jobEntity.keywords.any().keyword.equalsIgnoreCaseOrNull(keyword)
}
