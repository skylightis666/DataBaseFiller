package com.polykek.database.entities;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;

@Table
@Entity
@Getter
@Setter
public class Partition implements BaseEntity {
    @Id
    @PodamExclude
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer partitionId;

    @Column
    private String shape;

    @Column
    private Float cellResistance;

    @ManyToOne
    @PodamExclude
    @JoinColumn(name = "grid_id")
    private Grid grid;
}
