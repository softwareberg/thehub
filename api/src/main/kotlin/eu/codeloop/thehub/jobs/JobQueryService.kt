package eu.codeloop.thehub.jobs

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import eu.codeloop.thehub.base.containsIgnoreCaseOrNull
import eu.codeloop.thehub.jobs.model.JobEntity
import eu.codeloop.thehub.jobs.model.QJobEntity.jobEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class JobQueryService(private val jobsRepository: JobRepository) {

    fun findAll(title: String?, q: String?, hasStar: Boolean?, pageable: Pageable): Page<JobEntity> {
        val predicate = createPredicate(title, q, hasStar)
        return jobsRepository.findAll(predicate, pageable)
    }

    private fun createPredicate(title: String?, q: String?, hasStar: Boolean?): Predicate {
        val containsTitle = containsInTitle(title)
        val search = searchPredicate(q)
        val hasStarPredicate = hasStarPredicate(hasStar)
        return BooleanBuilder().and(containsTitle).and(search).and(hasStarPredicate).and(notDeleted())
    }

    private fun searchPredicate(q: String?): Predicate = BooleanBuilder().or(containsInTitle(q)).or(containsInDescription(q))
    private fun containsInDescription(description: String?): Predicate? = jobEntity.description.containsIgnoreCaseOrNull(description)
    private fun containsInTitle(title: String?): Predicate? = jobEntity.title.containsIgnoreCaseOrNull(title)
    private fun hasStarPredicate(hasStar: Boolean?): Predicate? = if (hasStar == null) null else jobEntity.hasStar.eq(hasStar)
    private fun notDeleted(): Predicate? = jobEntity.isDeleted.isFalse
}
