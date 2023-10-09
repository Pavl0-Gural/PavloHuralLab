package com.softserve.itacademy.TestService;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.State;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.service.impl.StateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StateServiceImplTest {
    @Mock
    private StateRepository stateRepository;

    @InjectMocks
    private StateServiceImpl stateService;

    State expected;

    @BeforeEach
    void setUp() {
        expected = new State();
        expected.setId(1L);
        expected.setName("NEW");
    }

    @Test
    void createTest() {
        when(stateRepository.save(expected)).thenReturn(expected);

        State actual = stateService.create(expected);
        Assertions.assertNotNull(actual);

        verify(stateRepository, times(1)).save(expected);
    }

    @Test
    void createTestWhenNullThrowsException() {
        when(stateRepository.save(null)).thenThrow(IllegalArgumentException.class);

        Throwable throwable = Assertions.assertThrows(NullEntityReferenceException.class,
                () -> stateService.create(null));
        String expectedMessage = "State cannot be 'null'";
        assertEquals(expectedMessage, throwable.getMessage());

//        verify(stateRepository, never()).save(null);
    }

    @Test
    void readByIdTest() {
        when(stateRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

        State actual = stateService.readById(expected.getId());
        Assertions.assertNotNull(actual);

        verify(stateRepository, times(1)).findById(expected.getId());

    }

    @Test
    void readByIdWhenNotFoundThrowsExceptionTest() {
        when(stateRepository.findById(expected.getId())).thenReturn(Optional.empty());

     Throwable throwable = Assertions.assertThrows(EntityNotFoundException.class,
              () -> stateService.readById(expected.getId()));
     String expectedMessage = "State with id " + expected.getId() + " not found";
     Assertions.assertEquals(expectedMessage, throwable.getMessage());

        verify(stateRepository, times(1)).findById(expected.getId());

    }

    @Test
    void updateTest() {
        when(stateRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
        when(stateRepository.save(expected)).thenReturn(expected);

        State actual = stateService.update(expected);
        Assertions.assertNotNull(actual);

        verify(stateRepository, times(1)).findById(expected.getId());
        verify(stateRepository, times(1)).save(expected);
    }

    @Test
    void updateWhenNullExceptionThrownTest() {

        Throwable throwable = assertThrows(NullEntityReferenceException.class, ()
                -> stateService.update(null)
        );
        String expectedMessage = "State cannot be 'null'";

        assertEquals(expectedMessage, throwable.getMessage());
        verifyNoInteractions(stateRepository);

    }

    @Test
    void updateWhenNotFoundExceptionThrownTest() {

        when(stateRepository.findById(expected.getId())).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(EntityNotFoundException.class, ()
                -> stateService.update(expected)
        );
        String expectedMessage = "State with id " + expected.getId() + " not found";
        Assertions.assertEquals(expectedMessage, throwable.getMessage());

        verify(stateRepository, times(1)).findById(expected.getId());
        verifyNoMoreInteractions(stateRepository);

    }

    @Test
    void delete() {
        when(stateRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

        stateService.delete(expected.getId());

        verify(stateRepository, times(1)).findById(expected.getId());
        verify(stateRepository, times(1)).delete(expected);
    }



    @Test
    void getByNameTest() {
        when(stateRepository.getByName(expected.getName())).thenReturn(expected);

        State actual = stateService.getByName(expected.getName());
        Assertions.assertNotNull(actual);

        verify(stateRepository, times(1)).getByName(expected.getName());

    }

    @Test
    void getByNameWhenNotFoundThrowsExceptionTest() {
        when(stateRepository.getByName(expected.getName())).thenReturn(null);

       Throwable throwable = Assertions.assertThrows(EntityNotFoundException.class,
                () -> stateService.getByName(expected.getName()));
       String expectedMessage = "State with name '" + expected.getName() + "' not found";
       Assertions.assertEquals(expectedMessage, throwable.getMessage());

        verify(stateRepository, times(1)).getByName(expected.getName());

    }

    @Test
    void getAll() {
        List<State> expectedList = Arrays.asList(expected);
        when(stateRepository.getAll()).thenReturn(expectedList);

        List<State> actualList = stateService.getAll();
        Assertions.assertEquals(expectedList.size(), actualList.size());

        verify(stateRepository, times(1)).getAll();

    }
}