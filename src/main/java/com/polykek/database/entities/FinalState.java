package com.polykek.database.entities;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;

@Table
@Entity
@Getter
@Setter
public class FinalState implements BaseEntity {
    @Id
    @PodamExclude
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer finalStateId;

    @Column
    private Boolean reversibility;

    @Column
    private Boolean homogeneity;

    @Column
    private Boolean stability;

    @Column
    private Boolean cyclicality;
}
