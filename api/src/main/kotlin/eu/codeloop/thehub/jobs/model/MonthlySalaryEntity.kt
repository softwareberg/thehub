package eu.codeloop.thehub.jobs.model

import java.util.ArrayList
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "monthly_salaries")
class MonthlySalaryEntity {

    @Id
    @Column(name = "monthly_salary", nullable = false)
    lateinit var monthlySalary: String

    @OneToMany
    @JoinColumn(
        name = "monthly_salary",
        referencedColumnName = "monthly_salary",
        insertable = false,
        updatable = false
    )
    val jobs: MutableList<JobEntity> = ArrayList()
}
