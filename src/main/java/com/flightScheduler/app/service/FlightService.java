package com.flightScheduler.app.service;

import com.flightScheduler.app.model.Flight;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface FlightService {

    Iterable<Flight> getAllFlights();

    Flight viewFlight(int id);
}
