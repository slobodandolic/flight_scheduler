package com.flightScheduler.app.dao;

import com.flightScheduler.app.model.Gate;
import com.flightScheduler.app.model.Occupied;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
public class GateDaoTest {
    @Autowired
    private GateDao gateDao;

    @Test
    public void testSave() {
        Gate gate = new Gate(1, Occupied.FREE, LocalTime.now(), "123ABC");
        gate = gateDao.save(gate);
        assertNotNull(gate.getId());
    }

    @Test
    public void testFindById() {
        Optional<Gate> gateOptional = gateDao.findById(1);
        assertTrue(gateOptional.isPresent());
        assertEquals(1, gateOptional.get().getId());
    }

    @Test
    public void testFindByNonExistingId() {
        Optional<Gate> gateOptional = gateDao.findById(6);
        assertFalse(gateOptional.isPresent());
    }

    @Test
    public void testFindAll() {
        List<Integer> dbIdOfGates = gateDao.findAll().stream()
                .map(Gate::getId)
                .collect(Collectors.toList());

        assertThat(dbIdOfGates, containsInAnyOrder(1, 2, 3, 4, 5));
    }

    @Test
    public void testCount() {
        assertEquals(5, gateDao.count());
    }


}
