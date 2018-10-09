package com.softwareberg.thehub.jobs.model

import java.util.ArrayList
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "positions_types")
class PositionsTypeEntity {

    @Id
    @Column(name = "position_type", nullable = false)
    lateinit var positionType: String

    @OneToMany
    @JoinColumn(
        name = "position_type",
        referencedColumnName = "position_type",
        insertable = false,
        updatable = false
    )
    var jobs: MutableList<JobEntity> = ArrayList()
}
