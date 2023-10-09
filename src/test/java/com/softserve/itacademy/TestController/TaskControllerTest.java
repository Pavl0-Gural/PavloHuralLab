package com.softserve.itacademy.TestController;


import com.softserve.itacademy.dto.TaskDto;
import com.softserve.itacademy.dto.TaskTransformer;
import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.service.StateService;
import com.softserve.itacademy.service.ToDoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToDoService toDoService;

    @Autowired
    private StateService stateService;


    @Test
    @Transactional
    public void createGetTest() throws Exception {
        mockMvc.perform(get("/tasks/create/todos/{todo_id}",7)
                        .param("todoId", String.valueOf(7)))
                .andExpect(MockMvcResultMatchers.model().attributeExists("task"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("todo"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("priorities"))
                .andExpect(MockMvcResultMatchers.view().name("create-task"));
    }

    @Test
    public void testPostCreate() throws Exception {

        Task task = new Task();
        task.setName("Task1");
        task.setPriority(Priority.LOW);
        task.setTodo(toDoService.readById(7L));
        task.setState(stateService.getByName("New"));
        task.setId(5);

        TaskDto taskDto = TaskTransformer.convertToDto(task);


        mockMvc.perform(post("/tasks/create/todos/{todo_id}", 7)
                        .flashAttr("task", taskDto))
                        .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(get("/tasks/{task_id}/delete/todos/{todo_id}",5,7))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

}
