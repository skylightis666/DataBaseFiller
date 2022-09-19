package com.polykek.database.entities;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;

@Table
@Entity
@Getter
@Setter
public class Step implements BaseEntity {
    @Id
    @PodamExclude
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer stepId;

    @Column
    private Integer ordinal;

    @PodamExclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cell_automaton_id")
    private CellAutomaton cellAutomaton;
}
