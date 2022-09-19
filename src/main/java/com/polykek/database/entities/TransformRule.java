package com.polykek.database.entities;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table
public class TransformRule implements BaseEntity {
    @Id
    @PodamExclude
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer transformRuleId;

    @Column
    private Integer decimalForm;

    @Column
    private String binaryFrom;
}
