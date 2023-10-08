package com.softserve.itacademy;

import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(JUnitPlatform.class)
public class ToDoServiceTest {
    private static UserService userService;
    private static ToDoService toDoService;

    @BeforeAll
    public static void setupBeforeClass() throws Exception
    {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext( Config.class );
        userService = annotationConfigContext.getBean( UserService.class );
        toDoService = annotationConfigContext.getBean( ToDoService.class );
        annotationConfigContext.close();
    }

    @AfterEach
    public void beforeEach() throws Exception
    {
        userService.getAll().clear();
        toDoService.getAll().clear();
    }

    @Test
    public void checkAddToDo()
    {
        User user1 = new User( "firstName1", "lastName1", "email1", "password1" );
        userService.addUser( user1 );

        LocalDateTime createdAt1 = LocalDateTime.now();
        ToDo expected = new ToDo( "Write Program", createdAt1, user1 );
        ToDo actual = toDoService.addTodo( expected, user1 );

        Assertions.assertEquals( actual, expected );
    }
    @Test
    public void checkUpdateTodo() {
        User user1 = new User("firstName1","lastName1","email1","password1");

        LocalDateTime createdAt1 = LocalDateTime.now();
        ToDo actual = new ToDo("Write Program",createdAt1, user1);
        List<ToDo> todoes = new ArrayList<>();
        todoes.add(actual);
        user1.setMyTodos(todoes);
        userService.addUser(user1);
        ToDo expected = toDoService.updateTodo(actual);

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void checkDeleteTodo() {
        User user1 = new User("firstName1","lastName1","email1","password1");

        LocalDateTime createdAt1 = LocalDateTime.now();
        ToDo todo = new ToDo("Write Program",createdAt1, user1);
        List<ToDo> todoes = new ArrayList<>();
        todoes.add(todo);
        user1.setMyTodos(todoes);
        userService.addUser(user1);

        int actualSize = toDoService.getAll().size();

        toDoService.deleteTodo(todo);
        int expectedSize = actualSize-1;

        Assertions.assertEquals(expectedSize, actualSize-1);
    }
    @Test
    public void checkGetAll() {

        User user1 = new User("firstName1","lastName1","email1","password1");

        LocalDateTime createdAt1 = LocalDateTime.now();
        ToDo todo1 = new ToDo("Write Program",createdAt1, user1);

        List<ToDo> todes1 = new ArrayList<>();
        todes1.add(todo1);
        user1.setMyTodos(todes1);

        User user2 = new User("firstName2","lastName2","email2","password2");

        LocalDateTime createdAt2 = LocalDateTime.now();
        ToDo todo2 = new ToDo("Write Program",createdAt2, user2);

        List<ToDo> todes2 = new ArrayList<>();
        todes1.add(todo2);
        user2.setMyTodos(todes2);

        User user3 = new User("firstName3","lastName3","email3","password3");

        LocalDateTime createdAt3 = LocalDateTime.now();
        ToDo todo3 = new ToDo("Write Program",createdAt3, user3);

        List<ToDo> todes3 = new ArrayList<>();
        todes1.add(todo3);
        user3.setMyTodos(todes3);

        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);

        List<ToDo> actual = new ArrayList<>();
        actual.add(todo1);
        actual.add(todo2);
        actual.add(todo3);

        List<ToDo> expected = toDoService.getAll();

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void checkGetByUser() {
        List<ToDo> actual = new ArrayList<>();
        User user1 = new User("firstName1","lastName1","email1","password1");

        LocalDateTime createdAt1 = LocalDateTime.now();
        ToDo todo1 = new ToDo("Write Program",createdAt1, user1);

        List<ToDo> todes1 = new ArrayList<>();
        todes1.add(todo1);
        user1.setMyTodos(todes1);
        actual.add(todo1);

        userService.addUser(user1);
        List<ToDo> expected;
        expected = toDoService.getByUser(user1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void checkGetByUserTitle() {
        List<Task> task1 = new ArrayList<>();
        task1.add(new Task("Write class", Priority.HIGH));
        task1.add(new Task("Write method to class",Priority.MEDIUM));
        task1.add(new Task("Write test",Priority.LOW));
        User user1 = new User("firstName1","lastName1","email1","password1");
        LocalDateTime createdAt1 = LocalDateTime.now();
        ToDo actual = new ToDo("Write Program",createdAt1, user1);
        List<ToDo> todoes = new ArrayList<>();
        todoes.add(actual);
        user1.setMyTodos(todoes);
        userService.addUser(user1);

        ToDo expected =  toDoService.getByUserTitle(user1, "Write Program");

        Assertions.assertEquals(expected, actual);
    }
}
