package com.polykek.database.entities;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;

@Table
@Entity
@Getter
@Setter
public class Structure implements BaseEntity {
    @Id
    @PodamExclude
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer structureId;

    @Column
    private Boolean reversibility;

    @Column
    private Boolean stability;

    @Column
    private Boolean cyclicality;

    @Column
    private Integer lifeTime;

    @Column
    private String type;

    @ManyToOne
    @PodamExclude
    @JoinColumn(name = "start_state_id")
    private StartState startState;

    @ManyToOne
    @PodamExclude
    @JoinColumn(name = "intermediate_state_id")
    private IntermediateState intermediateState;

    @ManyToOne
    @PodamExclude
    @JoinColumn(name = "final_state_id")
    private FinalState finalState;
}
