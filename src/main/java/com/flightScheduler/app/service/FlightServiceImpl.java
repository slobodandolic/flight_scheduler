package com.flightScheduler.app.service;

import com.flightScheduler.app.dao.FlightDao;
import com.flightScheduler.app.model.Flight;
import com.flightScheduler.app.model.Gate;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightDao flightDao;

    @Override
    public Iterable<Flight> getAllFlights() {
        return flightDao.findAll() ;
    }

    @Override
    public Flight viewFlight(int id) {
        Optional<Flight> findById = flightDao.findById(id);
        return findById.orElse(null);
    }
}
