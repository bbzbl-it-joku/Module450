package com.example.m450.lb1.domain.aircraft;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.m450.lb1.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper=true, includeFieldNames=true)
@Table("aircraft")
public class Aircraft extends BaseEntity {
    @Column("registration_code")
    private String registrationCode;
    @Column("registration_prefix")
    private String registrationPrefix;
    @Column("airline_id")
    private Long airlineId;
    private String type;
    private int capacity;
}