package com.flightScheduler.app.dao;

import com.flightScheduler.app.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightDao extends JpaRepository <Flight, Integer> {
}
