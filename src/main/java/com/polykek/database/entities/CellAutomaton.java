package com.polykek.database.entities;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table
public class CellAutomaton implements BaseEntity {
    @Id
    @PodamExclude
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer cellAutomatonId;

    @Column
    public String type;

    @PodamExclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transform_rule_id")
    public TransformRule transformRule;

    @PodamExclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grid_id")
    public Grid grid;

    @PodamExclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "start_state_id")
    public StartState startState;
}
