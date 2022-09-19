package com.polykek.database.entities;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;

@Table
@Entity
@Getter
@Setter
public class StartState implements BaseEntity {
    @Id
    @PodamExclude
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer startStateId;

}
