package com.flightScheduler.app.model;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="gate")
public class Gate {

    @Id
    private Integer id;

    @Column(name="occupied")
    private Occupied occupied;

    @Column(name="time_available")
    private LocalTime time_available;

    @Column(name="flight_code")
    private String flight_code;

    public Gate() {
    }

    public Gate(Integer id, Occupied occupied, LocalTime time_available, String flight_code) {
        this.id = id;
        this.occupied = occupied;
        this.time_available = time_available;
        this.flight_code = flight_code;
    }

    public String getFlight_code() {
        return flight_code;
    }
    public void setFlight_code(String flight_code) {
        this.flight_code = flight_code;
    }
    public LocalTime getTime_available() {
        return time_available;
    }
    public void setTime_available(LocalTime time_available) {
        this.time_available = time_available;
    }
    public Occupied getOccupied() {
        return occupied;
    }
    public void setOccupied(Occupied occupied) {
        this.occupied = occupied;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
