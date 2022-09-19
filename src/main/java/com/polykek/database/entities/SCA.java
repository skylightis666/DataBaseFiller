package com.polykek.database.entities;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table
public class SCA implements BaseEntity {
    @Id
    @PodamExclude
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer scaId;

    @ManyToOne
    @PodamExclude
    @JoinColumn(name = "cell_automaton_id")
    private CellAutomaton cellAutomaton;

    @ManyToOne
    @PodamExclude
    @JoinColumn(name = "science_id")
    private Science science;
}
