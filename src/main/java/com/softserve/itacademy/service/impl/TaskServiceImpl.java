package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TaskServiceImpl implements TaskService
{

    private ToDoService toDoService;

    @Autowired
    public TaskServiceImpl( ToDoService toDoService )
    {
        this.toDoService = toDoService;
    }

    public Task addTask( Task task, ToDo todo )
    {
        todo.getTasks().add(task);
        return task;
    }

    public Task updateTask(Task task)
    {
        for (ToDo toDo : toDoService.getAll()) {
            for (Task t : toDo.getTasks()) {
                if (Objects.equals(task.getName(), t.getName())) {
                    t.setPriority(task.getPriority());
                }
            }
        }
        return task;
    }

    public void deleteTask(Task task) {
        for (ToDo toDo : toDoService.getAll()) {
            List<Task> tasks = toDo.getTasks();

            for (int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);
                if (Objects.equals(task.getName(), t.getName())) {
                    toDo.getTasks().remove(t);
                }
            }
        }
    }

    public List<Task> getAll() {
        List<Task> tasks = new ArrayList<>();

        for (ToDo toDo : toDoService.getAll()) {
            tasks.addAll(toDo.getTasks());
        }

        return tasks;
    }

    public List<Task> getByToDo(ToDo todo) {
        List<Task> tasks = new ArrayList<>();

        for (ToDo currentTodo : toDoService.getAll()) {
            if (Objects.equals(todo, currentTodo)) {
                tasks.addAll(currentTodo.getTasks());
            }
        }

        return tasks;
    }

    public Task getByToDoName(ToDo todo, String name) {
        for (ToDo t : toDoService.getAll()) {
            if (t.equals(todo)) {
                for (Task task : t.getTasks()) {
                    if (task.getName().equals(name)) {
                        return task;
                    }
                }
            }
        }
        return null;
    }

    public Task getByUserName(User user, String name) {
        List<ToDo> todo = toDoService.getByUser(user);
        for (ToDo t : todo) {
            for (Task task : t.getTasks()) {
                if (task.getName().equals(name)) {
                    return task;
                }
            }
        }
        return null;
    }
}
