package com.example.m450.lb1.domain.seat;

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
@Table("seat")
public class Seat extends BaseEntity {
    private String seatNumber;

    @Column("flight_id")
    private Long flightId;

    @Column("passenger_id")
    private Long passengerId;
}