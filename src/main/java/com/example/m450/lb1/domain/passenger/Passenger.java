package com.example.m450.lb1.domain.passenger;

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
@ToString(callSuper = true, includeFieldNames = true)
@Table("passenger")
public class Passenger extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
}