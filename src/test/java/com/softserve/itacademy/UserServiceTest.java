package com.softserve.itacademy;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

@RunWith( JUnitPlatform.class )
public class UserServiceTest
{
    private static UserServiceImpl userService;

    @BeforeAll
    public static void setupBeforeClass() throws Exception
    {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext( Config.class );
        userService = annotationConfigContext.getBean( UserServiceImpl.class );
        annotationConfigContext.close();
    }

    @AfterEach
    public void beforeEach() throws Exception
    {
        userService.getAll().clear();
    }

    @Test
    public void checkAddUser()
    {
        User user = new User( "firstName", "lastName", "email", "password" );
        User expected = new User( "firstName", "lastName", "email", "password" );
        User actual = userService.addUser( user );
        Assertions.assertEquals( expected, actual, "Wrong logic for add method in UserService" );
    }

    @Test
    public void checkUpdateUser()
    {

        User initialUser = new User( "firstName", "lastName", "email", "password" );
        userService.addUser( initialUser );
        User userUpdate = new User( "firstName1", "lastName1", "email1", "password1" );
        User updatedUser = userService.updateUser( userUpdate );

        User initialUser2 = new User( "firstName2", "lastName2", "email2", "password2" );
        userService.addUser( initialUser2 );
        User userUpdate2 = new User( "firstName22", "lastName22", "email22", "password22" );
        User updatedUser2 = userService.updateUser( userUpdate2 );

        User expected = new User( "firstName1", "lastName1", "email1", "password1" );
        User expected2 = new User( "firstName22", "lastName22", "email22", "password22" );
        Assertions.assertEquals( expected, updatedUser, "Expected user is different to expected. Not correct update" );
        Assertions.assertEquals( expected2, updatedUser2, "Expected user is different to expected. Not correct update" );
        Assertions.assertEquals( 2, userService.getAll().size(), "Wrong size of users list" );
    }

    @Test
    public void checkDeleteUser()
    {
        User initialUser = new User( "firstName", "lastName", "email", "password" );
        User added = userService.addUser( initialUser );
        int initialsize = userService.getAll().size();
        Assert.assertEquals( initialsize, userService.getAll().size() );

        userService.deleteUser( initialUser );
        Assertions
            .assertEquals( ( initialsize - 1 ), userService.getAll().size(), "Expected size of user list " + ( initialsize - 1 ) + " but found " + userService.getAll().size() );
    }

    @Test
    public void checkGetAllUsers()
    {
        User userExpected1 = new User( "firstName3", "lastName3", "email3", "password3" );
        User userExpected2 = new User( "firstName4", "lastName4", "email4", "password4" );
        User userExpected3 = new User( "firstName5", "lastName5", "email5", "password5" );
        userService.addUser( userExpected1 );
        userService.addUser( userExpected2 );
        userService.addUser( userExpected3 );

        List<User> users = userService.getAll();

        User userActual1 = users.get( 0 );
        User userActual2 = users.get( 1 );
        User userActual3 = users.get( 2 );

        Assert.assertEquals( 3, userService.getAll().size() );

        Assertions.assertEquals( userExpected1, userActual1, "Expected and actual user are different" );
        Assertions.assertEquals( userExpected2, userActual2, "Expected and actual user are different" );
        Assertions.assertEquals( userExpected3, userActual3, "Expected and actual user are different" );
    }

    @Test
    public void checkGetUserById()
    {
        User expected = new User( "firstName", "lastName", "email", "password" );
        userService.addUser( expected );

        User actual = userService.getUserById( 1 );

        Assertions.assertEquals( expected, actual, "Expected and actual results are different" );
    }

    @Test
    public void checkUpdateUserById()
    {
        User user = new User( "firstName", "lastName", "email", "password" );
        userService.addUser( user );

        User expected = userService.updateUserById( 1, new User( "firstName Updated", "lastName Updated", "email Updated", "password Updated" ) );

        User actual = userService.getUserById( 1 );

        Assertions.assertEquals( expected, actual, "Expected and actual results are different" );
    }

    @Test
    public void checkDeleteUserById()
    {
        User user = new User( "firstName", "lastName", "email", "password" );
        userService.addUser( user );
        userService.deleteUserById( 1 );

        Assertions.assertTrue( userService.getAll().isEmpty(), "Expected and actual results are different" );
    }
}
