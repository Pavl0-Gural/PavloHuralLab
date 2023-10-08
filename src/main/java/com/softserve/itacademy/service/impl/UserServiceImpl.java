package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private List<User> users;
    private int index;

    public UserServiceImpl() {
        users = new ArrayList<>();
    }

    @Override
    public User addUser(User user)
    {
        users.add( user );
        index = users.indexOf( user );
        return user;
    }

    @Override
    public User updateUser(User user)
    {
        User toUpdate = users.get( index );
        users.remove( toUpdate );
        users.add( index, user );
        return user;
    }

    @Override
    public void deleteUser( User user )
    {
        users.remove( user );
    }

    @Override
    public List<User> getAll()
    {
        return users;
    }

    public User getUserById( int id )
    {
        int index = id - 1;
        if( index < users.size() )
        {
            User user = users.get( index );
            return user;
        }
        else
        {
            throw new IllegalArgumentException( "No user with such id" );
        }
    }

    public User updateUserById( int id, User user )
    {
        int index = id - 1;
        if( index < users.size() )
        {
            User toUpdate = users.get( index );
            users.remove( toUpdate );
            users.add( index, user );
            return user;
        }
        else
        {
            throw new IllegalArgumentException( "No user with such id" );
        }
    }

    public void deleteUserById( int id )
    {
        int index = id - 1;
        if( index < users.size() )
        {
            users.remove( index );
        }
        else
        {
            throw new IllegalArgumentException( "No user with such id" );
        }
    }
}
