package com.softserve.itacademy.TestService;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.TaskRepository;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.service.impl.TaskServiceImpl;
import com.softserve.itacademy.service.impl.ToDoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ToDoServiceImplTest {

    @Mock
    ToDoRepository toDoRepository;

    @InjectMocks
    ToDoServiceImpl toDoService;

    private ToDo expected;

    @BeforeEach
    void setUp() {
        expected = new ToDo();
        expected.setId(1L);
        expected.setTitle("todolist");
        expected.setCreatedAt(LocalDateTime.now());

    }

    @Test
    void createTest() {
        when(toDoRepository.save(expected)).thenReturn(expected);

        ToDo actual = toDoService.create(expected);
        Assertions.assertNotNull(actual);

        verify(toDoRepository, times(1)).save(expected);
    }

    @Test
    void createTaskWhenNullExceptionThrownTest() {
        when(toDoRepository.save(null)).thenThrow(IllegalArgumentException.class);


        Exception exception = assertThrows(NullEntityReferenceException.class, ()
                -> toDoService.create(null)
        );
        String expectedMessage = "To-Do cannot be 'null'";
        assertEquals(expectedMessage, exception.getMessage());
        verifyNoMoreInteractions(toDoRepository);

    }

    @Test
    void readByIdTest() {
        when(toDoRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

        ToDo actual = toDoService.readById(expected.getId());
        Assertions.assertNotNull(actual);
        verify(toDoRepository, times(1)).findById(expected.getId());
    }

    @Test
    void readByIdWhenNotFoundTest() {

        when(toDoRepository.findById(expected.getId())).thenReturn(Optional.empty());


        Throwable throwable = Assertions.assertThrows(EntityNotFoundException.class,
                () -> toDoService.readById(expected.getId()));
        String expectedMessage = "To-Do with id " + expected.getId() + " not found";
        Assertions.assertEquals(expectedMessage, throwable.getMessage());

        verify(toDoRepository, times(1)).findById(expected.getId());

    }

    @Test
    void updateTest() {
        when(toDoRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
        when(toDoRepository.save(expected)).thenReturn(expected);

        ToDo actual = toDoService.update(expected);
        Assertions.assertNotNull(actual);

        verify(toDoRepository, times(1)).findById(expected.getId());
        verify(toDoRepository, times(1)).save(expected);
    }

    @Test
    void updateWhenNullExceptionThrownTest() {

        Throwable throwable = assertThrows(NullEntityReferenceException.class, ()
                -> toDoService.update(null)
        );
        String expectedMessage = "To-Do cannot be 'null'";

        assertEquals(expectedMessage, throwable.getMessage());
        verifyNoInteractions(toDoRepository);

    }

    @Test
    void updateWhenNotFoundExceptionThrownTest() {

        when(toDoRepository.findById(expected.getId())).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(EntityNotFoundException.class, ()
                -> toDoService.update(expected)
        );
        String expectedMessage = "To-Do with id " + expected.getId() + " not found";
        Assertions.assertEquals(expectedMessage, throwable.getMessage());

        verify(toDoRepository, times(1)).findById(expected.getId());
        verifyNoMoreInteractions(toDoRepository);

    }

    @Test
    void deleteTest() {
        when(toDoRepository.findById(expected.getId())).thenReturn(Optional.of(expected));


        toDoService.delete(expected.getId());

        verify(toDoRepository, times(1)).findById(expected.getId());
        verify(toDoRepository, times(1)).delete(expected);
    }

    @Test
    void getAll() {
        List<ToDo> expectedList = Arrays.asList(expected);
        when(toDoRepository.findAll()).thenReturn(expectedList);

        List<ToDo> actualList = toDoService.getAll();

        Assertions.assertEquals(expectedList.size(), actualList.size());
        verify(toDoRepository, times(1)).findAll();
    }

    @Test
    void getByUserId() {
        List<ToDo> expectedList = Arrays.asList(expected);
        when(toDoRepository.getByUserId(expected.getId())).thenReturn(expectedList);

        List<ToDo> actual = toDoService.getByUserId(expected.getId());

        Assertions.assertEquals(expectedList.size(), actual.size());
        verify(toDoRepository, times(1)).getByUserId(expected.getId());
    }
}