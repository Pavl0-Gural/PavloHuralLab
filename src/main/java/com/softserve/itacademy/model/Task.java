package com.softserve.itacademy.model;

public class Task {

    private String name;

    private Priority priority;

    public Task( String name, Priority priority )
    {
        this.name = name;
        this.priority = priority;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Priority getPriority()
    {
        return priority;
    }

    public void setPriority( Priority priority )
    {
        this.priority = priority;
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

        Task task = (Task) o;

        if( name != null ? !name.equals( task.name ) : task.name != null )
        {
            return false;
        }
        return priority == task.priority;
    }

    @Override
    public int hashCode()
    {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + ( priority != null ? priority.hashCode() : 0 );
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", priority=" + priority +
                '}';
    }
}
