package com.flightScheduler.app.service;

import com.flightScheduler.app.model.Flight;
import org.springframework.http.ResponseEntity;

public interface FlightService {

    Iterable<Flight> getAllFlights();

    ResponseEntity<Flight> viewFlight(int id);
}
