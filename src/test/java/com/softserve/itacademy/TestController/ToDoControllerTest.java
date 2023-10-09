package com.softserve.itacademy.TestController;

import com.softserve.itacademy.controller.ToDoController;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ToDoControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;
    @Autowired
    ToDoService toDoService;
    @Autowired
    TaskService taskService;
    @Autowired
    ToDoController toDoController;


    @Test
    void createGetTest() throws Exception {
        long ownerId = 4L;

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/create/users/" + ownerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("todo"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ownerId"))
                .andExpect(MockMvcResultMatchers.model().attribute("ownerId", ownerId))
                .andExpect(MockMvcResultMatchers.view().name("create-todo"));

    }

    @Test
    @Transactional
    void createPostTest() throws Exception {
        long ownerId = 4L;

        mockMvc.perform(MockMvcRequestBuilders.post("/todos/create/users/" + ownerId)
                        .param("title", "todo1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/todos/all/users/" + ownerId));

    }

    @Test
    @Transactional
    void readTest() throws Exception {
        long userId = 4L;
        long toDoId = 7L;
        ToDo todo = toDoService.readById(toDoId);
        List<Task> tasks = taskService.getByTodoId(toDoId);
        List<User> users = userService.getAll().stream()
                .filter(user -> user.getId() != userId).collect(Collectors.toList());


        mockMvc.perform(MockMvcRequestBuilders.get("/todos/" + toDoId + "/tasks"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("todo"))
                .andExpect(MockMvcResultMatchers.model().attribute("todo", todo))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.model().attribute("users", users))
                .andExpect(MockMvcResultMatchers.model().attributeExists("tasks"))
                .andExpect(MockMvcResultMatchers.model().attribute("tasks", tasks))
                .andExpect(MockMvcResultMatchers.view().name("todo-tasks"));
    }

    @Test
    @Transactional
    void updateGetTest() throws Exception {
        long todoId = 7L;
        long ownerId = 4L;
        ToDo todo = toDoService.readById(todoId);

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/" + todoId + "/update/users/" + ownerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("todo"))
                .andExpect(MockMvcResultMatchers.model().attribute("todo", todo))
                .andExpect(MockMvcResultMatchers.view().name("update-todo"));
    }

    @Test
    @Transactional
    void updatePostTest() throws Exception {
        long ownerId = 4L;
        long todoId = 7L;

        mockMvc.perform(MockMvcRequestBuilders.post("/todos/" + todoId + "/update/users/" + ownerId)
                        .param("id",String.valueOf(todoId))
                        .param("title", "todo1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/todos/all/users/" + ownerId));
    }

    @Test
    void delete() throws Exception {
        long ownerId = 5L;
        long todoId = 10L;

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/" + todoId + "/delete/users/" + ownerId))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/todos/all/users/" + ownerId));
    }

    @Test
    @Transactional
    void getAllToDosTest() throws Exception {
        long userId = 4L;
        List<ToDo> expectedTodos = toDoService.getByUserId(userId);
        User expectedUser = userService.readById(userId);
        mockMvc.perform(MockMvcRequestBuilders.get("/todos/all/users/" + userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("todos"))
                .andExpect(MockMvcResultMatchers.model().attribute("todos", expectedTodos))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", expectedUser))
                .andExpect(MockMvcResultMatchers.view().name("todos-user"));
    }

    @Test
    void addCollaborator() throws Exception {
        long id = 7L;
        long userId = 4L;
        mockMvc.perform(MockMvcRequestBuilders.get("/todos/" + id + "/add")
                        .queryParam("user_id", String.valueOf(userId)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/todos/" + id + "/tasks"));
    }

    @Test
    void removeCollaborator() throws Exception {
        long id = 7L;
        long userId = 4L;
        mockMvc.perform(MockMvcRequestBuilders.get("/todos/" + id + "/remove")
                        .queryParam("user_id", String.valueOf(userId)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/todos/" + id + "/tasks"));
    }
}