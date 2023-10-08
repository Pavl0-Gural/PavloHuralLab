package com.softserve.itacademy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;

@Service
public class ToDoServiceImpl implements ToDoService {

    private UserService userService;

    @Autowired
    public ToDoServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public ToDo addTodo(ToDo todo, User user) {
        int i = 0;
        for(User s : userService.getAll()) {
            if (s.equals(user)) {
                List<ToDo> todoes = new ArrayList<>();
                todoes.add(todo);
                s.setMyTodos(todoes);
                return todo;
            }
            else if (i == userService.getAll().size()-1) {
                user.getMyTodos().add(todo);
                userService.addUser(user);
                return todo;
            }
            i++;
        }
        return null;
    }

    public ToDo updateTodo(ToDo todo) {
        for(User s : userService.getAll()) {
            s.getMyTodos().add(todo);
        }
        return todo;
    }

    public void deleteTodo(ToDo todo) {
        for (User s : userService.getAll()) {
            List<ToDo> toDoList = s.getMyTodos();
            for (ToDo t : toDoList) {
                if (t.equals(todo)) {
                    s.getMyTodos().remove(t);
                }
                if (toDoList.size()==0){
                    break;
                }
            }
        }
    }

    public List<ToDo> getAll() {
        List<ToDo> todo = new ArrayList<>();
        for (User s : userService.getAll()) {
            if (s.getMyTodos() != null) {
                List<ToDo> temp = new ArrayList<>(s.getMyTodos());
                todo.addAll(temp);
            }
        }
        return todo;
    }

    public List<ToDo> getByUser(User user) {
        List<ToDo> todo = new ArrayList<>();
        int i = 0;
        for(User s : userService.getAll()) {
            if (s.equals(user)) {
                todo.addAll(s.getMyTodos());
            }
            else if (i == userService.getAll().size()-1) {
                return null;
            }
            i++;
        }
        return todo;
    }

    public ToDo getByUserTitle(User user, String title) {
        for(User s : userService.getAll()) {
            if (s.equals(user)) {
                for(ToDo todo : s.getMyTodos()) {
                    if (todo.getTitle().equals(title)) {
                        return todo;
                    }
                }
            }
        }
        return null;
    }
}
