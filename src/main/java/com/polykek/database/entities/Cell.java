package com.polykek.database.entities;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;

@Table
@Entity
@Getter
@Setter
public class Cell implements BaseEntity {
    @Id
    @PodamExclude
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer cellId;

    @Column
    private Integer state;

    @Column
    private Float weight;

    @PodamExclude
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "partition_id")
    private Partition partition;

    @PodamExclude
    @ManyToOne
    @JoinColumn(name = "start_state_id")
    private StartState startState;

    @PodamExclude
    @ManyToOne
    @JoinColumn(name = "intermediate_state_id")
    private IntermediateState intermediateState;

    @PodamExclude
    @ManyToOne
    @JoinColumn(name = "final_state_id")
    private FinalState finalState;
}
