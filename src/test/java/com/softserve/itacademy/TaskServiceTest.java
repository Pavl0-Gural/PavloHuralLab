package com.softserve.itacademy;

import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.impl.TaskServiceImpl;
import com.softserve.itacademy.service.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

@RunWith( JUnitPlatform.class )
public class TaskServiceTest
{
    private static UserServiceImpl userService;
    private static ToDoService toDoService;
    private static TaskService taskService;

    @BeforeAll
    public static void setupBeforeClass() throws Exception
    {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext( Config.class );
        userService = annotationConfigContext.getBean( UserServiceImpl.class );
        toDoService = annotationConfigContext.getBean( ToDoService.class );
        taskService = annotationConfigContext.getBean( TaskServiceImpl.class );
        annotationConfigContext.close();
    }

    @AfterEach
    public void beforeEach() throws Exception
    {
        userService.getAll().clear();
        taskService.getAll().clear();
    }

    @Test
    public void checkAddTask()
    {
        User user = new User( "f", "n", "e", "password" );
        String todoName = "Basic Todo";

        userService.addUser( user );
        LocalDateTime createdAt = LocalDateTime.now();

        ToDo toDo = new ToDo( todoName, createdAt, userService.getUserById( 1 ) );
        Task actualTask = new Task( "test", Priority.LOW );
        toDoService.addTodo(toDo, user);

        taskService.addTask( actualTask, toDo );

        Task expectedTask = toDoService.getByUserTitle(user, todoName).getTasks().get(0);
        Assertions.assertEquals( expectedTask, actualTask, "Not correct add");
    }

    @Test
    public void checkUpdateTask()
    {
        User user = new User( "f", "n", "e", "password" );
        Priority expectedPriority = Priority.HIGH;

        userService.addUser( user );
        LocalDateTime createdAt = LocalDateTime.now();

        ToDo toDo = new ToDo( "Basic Todo", createdAt, userService.getUserById( 1 ) );
        Task task = new Task( "task", Priority.LOW );

        toDoService.addTodo(toDo, user);

        taskService.addTask( task, toDo );

        Task expected = new Task( "task", expectedPriority );
        taskService.updateTask( expected );

        Task updatedTask = toDoService.getByUser(user).get(0).getTasks().get(0);

        Assertions.assertEquals( expectedPriority, updatedTask.getPriority(), "Not correct update priority" );
    }

    @Test
    public void checkDeleteTask()
    {
        User user = new User( "f", "n", "e", "password" );
        userService.addUser( user );
        LocalDateTime createdAt = LocalDateTime.now();

        ToDo toDo = new ToDo( "Basic Todo", createdAt, userService.getUserById( 1 ) );
        Task task = new Task( "task", Priority.LOW );
        Task task2 = new Task( "task2", Priority.LOW );
        Task task3 = new Task( "task3", Priority.LOW );

        toDoService.addTodo(toDo, user);
        taskService.addTask( task, toDo );
        taskService.addTask( task2, toDo );
        taskService.addTask( task3, toDo );

        taskService.deleteTask( task );
        Assertions.assertEquals( taskService.getAll().size(), 2, "Not correct delete" );
    }

    @Test
    public void checkGetAll() {
        User user = new User( "f", "n", "e", "password" );
        User user2 = new User( "f2", "n2", "e2", "password2" );
        String todoName = "Basic Todo";
        String todoName2 = "Basic Todo 2";

        userService.addUser( user );
        userService.addUser( user2 );
        LocalDateTime createdAt = LocalDateTime.now();

        ToDo toDo = new ToDo( todoName, createdAt, userService.getUserById( 1 ) );
        ToDo toDo2 = new ToDo( todoName2, createdAt, userService.getUserById( 2 ) );

        Task task = new Task( "test", Priority.LOW );
        Task task2 = new Task( "test 2", Priority.MEDIUM );

        toDoService.addTodo(toDo, user);
        toDoService.addTodo(toDo2, user2);

        taskService.addTask( task, toDo );
        taskService.addTask( task, toDo2 );
        taskService.addTask( task2, toDo2 );

        int actualSize = taskService.getAll().size();
        int expectedSize = 3;
        Assertions.assertEquals( expectedSize, actualSize, "Not correct getAll");
    }

    @Test
    public void checkGetByToDo()
    {
        User user = new User( "f", "n", "e", "password" );
        userService.addUser( user );

        LocalDateTime createdAt = LocalDateTime.now();

        ToDo toDo = new ToDo( "Basic Todo", createdAt, user );

        Task task = new Task( "Test Todo", Priority.LOW );
        Task task2 = new Task( "Test Todo 2", Priority.HIGH );

        toDoService.addTodo(toDo, user);
        taskService.addTask( task, toDo );
        taskService.addTask( task2, toDo );

        List<Task> actual = taskService.getByToDo( toDo );
        List<Task> expected = List.of(task, task2);

        Assertions.assertEquals( expected, actual, "Not correct getByToDo" );
    }

    @Test
    public void checkGetByToDoName()
    {
        User user = new User( "f", "n", "e", "password" );
        String taskName = "Test Todo";
        userService.addUser( user );

        LocalDateTime createdAt = LocalDateTime.now();

        ToDo toDo = new ToDo( "Basic Todo", createdAt, user );

        Task task = new Task( taskName, Priority.LOW );
        Task expected = new Task( taskName, Priority.LOW );

        toDoService.addTodo(toDo, user);
        taskService.addTask( task, toDo );

        Task actual = taskService.getByToDoName( toDo, taskName );

        Assertions.assertEquals( expected, actual, "Not correct getByToDoName" );
    }

    @Test
    public void checkGetByUserName()
    {
        User user = new User( "f", "n", "e", "password" );
        String taskName = "task";
        userService.addUser( user );

        LocalDateTime createdAt = LocalDateTime.now();

        ToDo toDo = new ToDo( "Basic Todo", createdAt, user );

        Task task = new Task( taskName, Priority.LOW );
        Task expected = new Task( taskName, Priority.LOW );

        toDoService.addTodo(toDo, user);
        taskService.addTask( task, toDo );

        Task actual = taskService.getByUserName( user, taskName );

        Assertions.assertEquals( expected, actual, "Not correct getByUserName" );
    }
}
