package com.softserve.itacademy.TestService;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.UserRepository;
import com.softserve.itacademy.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.ArrayList;
import java.util.Optional;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks UserServiceImpl userService;

    @Mock
    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createUserTest() {
        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setFirstName("FirstName");
        expectedUser.setLastName("LastName");
        expectedUser.setPassword("password");
        expectedUser.setEmail("some@email");

        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        User actualUser = userService.create(expectedUser);

        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    public void readByIdTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        User actualUser = userService.readById(1L);
        Assertions.assertNotNull(actualUser);
    }

    @Test
    public void updateUserTest() {
        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setFirstName("UpdateTest");
        updatedUser.setLastName("UpdateTest");
        updatedUser.setPassword("passwordUpdate");
        updatedUser.setEmail("update@email");


        when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        User actualUser = userService.update(updatedUser);

        Assertions.assertEquals(updatedUser, actualUser);
    }
    @Test
    public void deleteUserTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        User user = userService.readById(1L);
        userService.delete(1L);
        Mockito.verify(userRepository, times(1)).delete(user);

    }
    @Test
    public void getAllTest() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        userService.getAll();
        Mockito.verify(userRepository, times(1)).findAll();
    }
}
