package com.softserve.itacademy.TestRepository;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.RoleRepository;
import com.softserve.itacademy.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findByEmail() {
        User expected = new User();
        expected.setFirstName("Firstname");
        expected.setLastName("Lastname");
        expected.setEmail("newuser@email.com");
        expected.setPassword("pass");
        expected.setRole(roleRepository.getOne(1L));

        userRepository.save(expected);
        User actual = userRepository.getUserByEmail("newuser@email.com");
        System.out.println(actual);
        Assertions.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assertions.assertEquals(expected.getLastName(), actual.getLastName());
        Assertions.assertEquals(expected.getEmail(), actual.getEmail());
        Assertions.assertEquals(expected.getPassword(), actual.getPassword());

    }
}
