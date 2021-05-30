package com.flightScheduler.app.dao;

import com.flightScheduler.app.model.Flight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Assertions;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
public class FlightDaoTest {

    @Autowired
    FlightDao flightDao;

    @Test
    public void testSave() {
        Flight flight = new Flight(1, "123ABC");
        flight = flightDao.save(flight);
        assertNotNull(flight.getId());
    }

    @Test
    public void testCount() {
        long count = flightDao.count();
        assertEquals(count, 0);

        Flight flight = new Flight(1, "123ABC");
        flight = flightDao.save(flight);

        count = flightDao.count();
        assertEquals(count, 1);
    }

    @Test
    public void testFindById() {
        Flight flight = new Flight(1, "123ABC");
        flight = flightDao.save(flight);

        assertNotNull(flightDao.findById(1));
    }

    @Test
    public void testFindByNonExistingId () {
        Optional<Flight> flightOptional = flightDao.findById(1);
        assertFalse(flightOptional.isPresent());
    }
}
