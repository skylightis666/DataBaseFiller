package com.polykek.database.entities;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;

@Table
@Entity
@Getter
@Setter
public class IntermediateState implements BaseEntity {
    @Id
    @PodamExclude
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer intermediateStateId;

    @Column
    private Boolean reversibility;

    @Column
    private Integer previousStateId;

    @ManyToOne
    @PodamExclude
    @JoinColumn(name = "final_state_id")
    private FinalState finalState;

    @ManyToOne
    @PodamExclude
    @JoinColumn(name = "start_state_id")
    private StartState startState;
}
