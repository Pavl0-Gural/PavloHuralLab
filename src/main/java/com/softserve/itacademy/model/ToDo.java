package com.softserve.itacademy.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ToDo {

    private String title;

    private LocalDateTime createdAt;

    private User owner;

    private List<Task> tasks;

    public ToDo( String title, LocalDateTime createdAt, User owner )
    {
        this.title = title;
        this.createdAt = createdAt;
        this.owner = owner;
        tasks = new ArrayList<>();
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt( LocalDateTime createdAt )
    {
        this.createdAt = createdAt;
    }

    public User getOwner()
    {
        return owner;
    }

    public void setOwner( User owner )
    {
        this.owner = owner;
    }

    public List<Task> getTasks()
    {
        return tasks;
    }

    public void setTasks( List<Task> tasks )
    {
        this.tasks = tasks;
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

        ToDo toDo = (ToDo) o;

        if( title != null ? !title.equals( toDo.title ) : toDo.title != null )
        {
            return false;
        }
        if( createdAt != null ? !createdAt.equals( toDo.createdAt ) : toDo.createdAt != null )
        {
            return false;
        }
        if( owner != null ? !owner.equals( toDo.owner ) : toDo.owner != null )
        {
            return false;
        }
        return tasks != null ? tasks.equals( toDo.tasks ) : toDo.tasks == null;
    }

    @Override
    public int hashCode()
    {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + ( createdAt != null ? createdAt.hashCode() : 0 );
        result = 31 * result + ( owner != null ? owner.hashCode() : 0 );
        result = 31 * result + ( tasks != null ? tasks.hashCode() : 0 );
        return result;
    }
}
