package com.softserve.itacademy.TestService;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.repository.TaskRepository;
import com.softserve.itacademy.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskServiceImpl taskService;

    private Task expected;

    @BeforeEach
    void setUp() {
        expected = new Task();
        expected.setId(1L);
        expected.setName("task1");
        expected.setPriority(Priority.LOW);

    }

    @Test
    void createTest() {
        when(taskRepository.save(expected)).thenReturn(expected);

        Task actual = taskService.create(expected);
        Assertions.assertNotNull(actual);

        verify(taskRepository, times(1)).save(expected);
    }

    @Test
    void createTaskWhenNullExceptionThrownTest() {
        when(taskRepository.save(null)).thenThrow(IllegalArgumentException.class);


        Throwable throwable = assertThrows(NullEntityReferenceException.class, ()
                -> taskService.create(null)
        );
        String expectedMessage = "Task cannot be 'null'";
        assertEquals(expectedMessage, throwable.getMessage());
        verifyNoMoreInteractions(taskRepository);

    }


    @Test
    void readByIdTest() {

        when(taskRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

        Task actual = taskService.readById(expected.getId());
        Assertions.assertNotNull(actual);
        verify(taskRepository, times(1)).findById(expected.getId());

    }

    @Test
    void readByIdWhenNotFoundTest() {

        when(taskRepository.findById(expected.getId())).thenReturn(Optional.empty());


        Throwable throwable = Assertions.assertThrows(EntityNotFoundException.class,
                () -> taskService.readById(expected.getId()));
        String expectedMessage = "Task with id " + expected.getId() + " not found";
        Assertions.assertEquals(expectedMessage, throwable.getMessage());

        verify(taskRepository, times(1)).findById(expected.getId());

    }

    @Test
    void updateTest() {
        when(taskRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
        when(taskRepository.save(expected)).thenReturn(expected);

        Task actual = taskService.update(expected);
        Assertions.assertNotNull(actual);

        verify(taskRepository, times(1)).findById(expected.getId());
        verify(taskRepository, times(1)).save(expected);
    }

    @Test
    void updateWhenNullExceptionThrownTest() {

        Throwable throwable = assertThrows(NullEntityReferenceException.class, ()
                -> taskService.update(null)
        );
        String expectedMessage = "Task cannot be 'null'";

        assertEquals(expectedMessage, throwable.getMessage());
        verifyNoInteractions(taskRepository);

    }

    @Test
    void updateWhenNotFoundExceptionThrownTest() {

        when(taskRepository.findById(expected.getId())).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(EntityNotFoundException.class, ()
                -> taskService.update(expected)
        );
        String expectedMessage = "Task with id " + expected.getId() + " not found";
        Assertions.assertEquals(expectedMessage, throwable.getMessage());

        verify(taskRepository, times(1)).findById(expected.getId());
        verifyNoMoreInteractions(taskRepository);

    }

    @Test
    void deleteTest() {
        when(taskRepository.findById(expected.getId())).thenReturn(Optional.of(expected));


        taskService.delete(expected.getId());

        verify(taskRepository, times(1)).findById(expected.getId());
        verify(taskRepository, times(1)).delete(expected);
    }

    @Test
    void deleteWhenNotFoundTest() {
        when(taskRepository.findById(expected.getId())).thenReturn(Optional.empty());

        Throwable throwable = Assertions.assertThrows(EntityNotFoundException.class,
                () -> taskService.delete(expected.getId()));
        String expectedMessage = "Task with id " + expected.getId() + " not found";
        Assertions.assertEquals(expectedMessage, throwable.getMessage());

        verify(taskRepository, times(1)).findById(expected.getId());
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void getAll() {
        List<Task> expectedList = Arrays.asList(expected);
        when(taskRepository.findAll()).thenReturn(expectedList);

        List<Task> actualList = taskService.getAll();

        Assertions.assertEquals(expectedList.size(), actualList.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getByTodoId() {

        List<Task> expectedList = Arrays.asList(new Task());
        when(taskRepository.getByTodoId(expected.getId())).thenReturn(expectedList);

        List<Task> actual = taskService.getByTodoId(expected.getId());

        Assertions.assertEquals(expectedList.size(), actual.size());
        verify(taskRepository, times(1)).getByTodoId(anyLong());
    }
}