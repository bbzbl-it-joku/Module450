package com.example.m450.lb1.domain.airline;

import java.util.Set;

import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import com.example.m450.lb1.domain.BaseEntity;
import com.example.m450.lb1.domain.aircraft.Aircraft;

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
@Table("airline")
public class Airline extends BaseEntity {
    private String icao;
    private String name;
    private String country;

    // Optional: If you want to maintain the one-to-many relationship
    @MappedCollection(idColumn = "airline_id")
    private Set<Aircraft> aircrafts;
}