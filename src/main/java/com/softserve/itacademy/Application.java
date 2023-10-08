package com.softserve.itacademy;

import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.impl.TaskServiceImpl;
import com.softserve.itacademy.service.impl.ToDoServiceImpl;
import com.softserve.itacademy.service.impl.UserServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;

public class Application
{

    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext( Config.class );

        UserServiceImpl userService = annotationConfigContext.getBean( UserServiceImpl.class );
        ToDoServiceImpl toDoService = annotationConfigContext.getBean( ToDoServiceImpl.class );
        TaskServiceImpl taskService = annotationConfigContext.getBean( TaskServiceImpl.class );

        annotationConfigContext.close();

        //User service demonstration

        //Add user
        userService.addUser( new User( "Ivan", "Ivanov", "123@email.com", "password" ) );
        System.out.println( userService.getAll() );

        //Update recently added user
        userService.updateUser( new User( "Updated Ivan", "Updated Ivanov", "Updated@email.com1", "password1Updated" ) );
        System.out.println( userService.getAll() );

        //Add one more user
        userService.addUser( new User( "Petro", "Petrov", "petro@email.com1", "petro" ) );
        System.out.println( userService.getAll() );

        //Get user by id
        System.out.println(userService.getUserById( 2 ));

        //Update user by id
        userService.updateUserById( 2, new User( "Updated Petro", "Updated Petrov", "petroUpdated@email.com1", "petroUpdated" ) );
        System.out.println( userService.getAll() );

        //Delete user
        userService.deleteUser( new User( "Updated Petro", "Updated Petrov", "petroUpdated@email.com1", "petroUpdated" ) );
        System.out.println( userService.getAll() );

        //Delete user by id
        userService.deleteUserById( 1 );
        System.out.println( userService.getAll() );



        //Task service demonstration

        System.out.println("\n\n----Tasks Service----");

        // First, we create some user and assign empty todo-list to him
        User user = new User( "f", "n", "e", "password" );
        userService.addUser( user );

        String todoName = "Basic Todo";
        ToDo toDo = new ToDo( todoName, LocalDateTime.now(), userService.getUserById( 1 ) );
        toDoService.addTodo(toDo, user);

        // Adding task to existing ToDo:
        System.out.println("//Adding task to existing ToDo: (test, Priority.LOW)");
        Task task = new Task( "test", Priority.LOW );
        taskService.addTask( task, toDo );

        System.out.println(toDoService.getByUserTitle(user, todoName).getTasks().get(0));


        // Updating task
        System.out.println("\n//Updating task: (test, Priority.MEDIUM)");
        taskService.updateTask(new Task( "test", Priority.MEDIUM ));

        System.out.println(toDoService.getByUserTitle(user, todoName).getTasks().get(0));


        // Deleting task
        System.out.println("\n//Deleting task: (size of tasks list = 0)");
        taskService.deleteTask(new Task( "test", Priority.MEDIUM ));

        int tasksSize = toDoService.getByUserTitle(user, todoName).getTasks().size();

        System.out.println("Size of tasks list: " + tasksSize);

    }
}
