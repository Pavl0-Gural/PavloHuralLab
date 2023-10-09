package com.softserve.itacademy.TestService;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.repository.RoleRepository;
import com.softserve.itacademy.service.impl.RoleServiceImpl;
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
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RoleServiceTest {

    @InjectMocks RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository =  Mockito.mock(RoleRepository .class);

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void createRoleTest() {
        Role role = new Role();
        when(roleRepository.save(role)).thenReturn(role);
        Role actual = roleService.create(role);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(role, actual);
    }
    @Test
    public void readByIdTest() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(new Role()));
        Role actual = roleService.readById(1L);
        Assertions.assertNotNull(actual);
    }
    @Test
    public void updateRoleTest() {
        Role role = new Role();
        role.setName("Role1");

        when(roleRepository.findById(0L)).thenReturn(Optional.of(new Role()));
        when(roleRepository.save(role)).thenReturn(role);

        Role actualRole = roleService.update(role);

        Assertions.assertNotNull(actualRole);
        Assertions.assertEquals(role, actualRole);

    }
    @Test
    public void deleteRoleTest() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(new Role()));
        Role role = roleService.readById(1L);
        roleService.delete(1L);
        Mockito.verify(roleRepository, times(1)).delete(role);
    }
    @Test
    public void getAllRoleTest() {
        when(roleRepository.findAll()).thenReturn(new ArrayList<>());
        List<Role> roles = roleService.getAll();
        Mockito.verify(roleRepository, times(1)).findAll();
        Assertions.assertNotNull(roles);
    }

}
