package eu.codeloop.thehub.jobs.model

import java.util.ArrayList
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "equities")
class EquityEntity {

    @Id
    @Column(name = "equity", nullable = false)
    lateinit var equity: String

    @OneToMany
    @JoinColumn(
        name = "equity",
        referencedColumnName = "equity",
        insertable = false,
        updatable = false
    )
    val jobs: MutableList<JobEntity> = ArrayList()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EquityEntity) return false
        return this.equity == other.equity
    }

    override fun hashCode(): Int = equity.hashCode()
}
