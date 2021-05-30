package com.flightScheduler.app.service;

import com.flightScheduler.app.dao.FlightDao;
import com.flightScheduler.app.exceptions.RecordNotPresentException;
import com.flightScheduler.app.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FlightServiceImpl implements FlightService {

    private static final String NO_FLIGHT_FOUND = "No flight found with ID: ";

    @Autowired
    private FlightDao flightDao;

    @Override
    public Iterable<Flight> getAllFlights() {
        return flightDao.findAll();
    }

    @Override
    public ResponseEntity<Flight> viewFlight(int id) {
        Optional<Flight> findById = flightDao.findById(id);
        try {
            if (!findById.isPresent()) {
                throw new RecordNotPresentException(NO_FLIGHT_FOUND + id);
            }
            Flight flight = findById.get();
            return new ResponseEntity<Flight>(flight, HttpStatus.OK);
        } catch (RecordNotPresentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
