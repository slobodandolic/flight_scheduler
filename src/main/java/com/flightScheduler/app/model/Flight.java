package com.flightScheduler.app.model;

import javax.persistence.*;

@Entity
@Table(name="flight")
public class Flight {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name="flight_code")
    private String flight_code;

    public Flight() {
    }

    public Flight(Integer id, String flight_code) {
        this.id = id;
        this.flight_code = flight_code;
    }

    public String getFlight_code() {
        return flight_code;
    }

    public void setFlight_code(String flight_code) {
        this.flight_code = flight_code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
