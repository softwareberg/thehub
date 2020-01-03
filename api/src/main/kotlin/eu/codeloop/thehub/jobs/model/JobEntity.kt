package eu.codeloop.thehub.jobs.model

import java.time.OffsetDateTime
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "jobs")
class JobEntity {

    @Id
    @Column(name = "job_id", nullable = false)
    lateinit var jobId: String

    @Column(name = "company_id", nullable = false)
    lateinit var companyId: String

    @Column(name = "title", nullable = false)
    lateinit var title: String

    @Column(name = "description", nullable = false)
    lateinit var description: String

    @Column(name = "has_star", nullable = false)
    var hasStar: Boolean = false

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false

    @Column(name = "approved_at", nullable = false)
    lateinit var approvedAt: OffsetDateTime

    @ManyToOne
    @JoinColumn(
        name = "company_id",
        referencedColumnName = "company_id",
        insertable = false,
        updatable = false,
        nullable = false
    )
    lateinit var company: CompanyEntity

    @ManyToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(
        name = "equity",
        referencedColumnName = "equity",
        insertable = true,
        updatable = true,
        nullable = true
    )
    var equity: EquityEntity? = null

    @ManyToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(
        name = "monthly_salary",
        referencedColumnName = "monthly_salary",
        insertable = true,
        updatable = true,
        nullable = true
    )
    var monthlySalary: MonthlySalaryEntity? = null
}
