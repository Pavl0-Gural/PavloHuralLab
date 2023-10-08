package com.softserve.itacademy.model;

import java.util.List;

public class User {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private List<ToDo> myTodos;

    public User( String firstName, String lastName, String email, String password )
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public List<ToDo> getMyTodos()
    {
        return myTodos;
    }

    public void setMyTodos( List<ToDo> myTodos )
    {
        this.myTodos = myTodos;
    }

    @Override
    public boolean equals( Object o )
    {
        if( this == o )
        {
            return true;
        }
        if( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        User user = (User) o;

        if( firstName != null ? !firstName.equals( user.firstName ) : user.firstName != null )
        {
            return false;
        }
        if( lastName != null ? !lastName.equals( user.lastName ) : user.lastName != null )
        {
            return false;
        }
        if( email != null ? !email.equals( user.email ) : user.email != null )
        {
            return false;
        }
        if( password != null ? !password.equals( user.password ) : user.password != null )
        {
            return false;
        }
        return myTodos != null ? myTodos.equals( user.myTodos ) : user.myTodos == null;
    }

    @Override
    public int hashCode()
    {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + ( lastName != null ? lastName.hashCode() : 0 );
        result = 31 * result + ( email != null ? email.hashCode() : 0 );
        result = 31 * result + ( password != null ? password.hashCode() : 0 );
        result = 31 * result + ( myTodos != null ? myTodos.hashCode() : 0 );
        return result;
    }

    @Override
    public String toString()
    {
        return "User{" +
               "firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", email='" + email + '\'' +
               ", password='" + password + '\'' +
               '}';
    }
}
