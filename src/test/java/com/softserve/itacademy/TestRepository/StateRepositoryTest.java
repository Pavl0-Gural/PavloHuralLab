package com.softserve.itacademy.TestRepository;


import com.softserve.itacademy.model.State;
import com.softserve.itacademy.repository.StateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class StateRepositoryTest {
    @Autowired
    private StateRepository stateRepository;

    @Test
    public void getByNameTest() {
        State state = stateRepository.getByName("New");
        Assertions.assertNotNull(state);
    }
    @Test
    public void getAllTest() {
        List<State> states = stateRepository.getAll();
        Assertions.assertNotNull(states);
    }
}
