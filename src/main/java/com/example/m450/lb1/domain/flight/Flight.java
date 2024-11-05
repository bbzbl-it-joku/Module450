package com.example.m450.lb1.domain.flight;

import java.time.LocalDateTime;

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
@ToString(callSuper = true, includeFieldNames = true)
@Table("flight")
public class Flight extends BaseEntity {
    private String flightNumber;
    @Column("aircraft_id")
    private Long aircraftId;
    private LocalDateTime departureTime;
    private LocalDateTime estimatedArrivalTime;
    private String destination;
    private String origin;
}