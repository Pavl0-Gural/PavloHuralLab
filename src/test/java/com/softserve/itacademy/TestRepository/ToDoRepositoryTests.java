package com.softserve.itacademy.TestRepository;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.RoleRepository;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ToDoRepositoryTests {
    @Autowired
    private ToDoRepository toDoRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;


    @Test
    public void getBydUserIdTest() {
        ToDo toDo = new ToDo();
        toDo.setTitle("todo1");
        toDo.setCreatedAt(LocalDateTime.now());
        User user = new User();
        user.setFirstName("Firstname");
        user.setLastName("Lastname");
        user.setEmail("newuser@email.com");
        user.setPassword("pass");
        user.setRole(roleRepository.getOne(1L));
        List<ToDo> expected = Arrays.asList(toDo);
        user.setMyTodos(expected);
        user = userRepository.save(user);
        toDo.setOwner(user);
        toDoRepository.save(toDo);

        List<ToDo> actual = toDoRepository.getByUserId(user.getId());
        Assertions.assertIterableEquals(expected, actual);

    }
}
