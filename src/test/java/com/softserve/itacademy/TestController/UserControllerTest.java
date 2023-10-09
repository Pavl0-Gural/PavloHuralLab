package com.softserve.itacademy.TestController;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.RoleService;
import com.softserve.itacademy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Test
    @Transactional
    public void createGetTest() throws Exception {

        mockMvc.perform(get("/users/create"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.view().name("create-user"));
    }

    @Test
    public void createPostTest() throws Exception {
        User user = new User();
        user.setPassword("user123");
        user.setEmail("user@123.com");
        user.setFirstName("Fisrt");
        user.setLastName("Last");
        user.setPassword("user123");

        mockMvc.perform(post("/users/create")
                        .flashAttr("user", user))
                        .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                        .andExpect(MockMvcResultMatchers.redirectedUrl("/todos/all/users/" + user.getId()));

    }
    @Test
    @Transactional
    public void updateGetTest() throws Exception {
        User user = userService.readById(4);
        List<Role> roles  = roleService.getAll();

        mockMvc.perform(get("/users/{id}/update", 4))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("roles"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", user))
                .andExpect(MockMvcResultMatchers.model().attribute("roles", roles))
                .andExpect(MockMvcResultMatchers.view().name("update-user"));
    }
    @Test
    @Transactional
    public void updatePostTest() throws Exception {
        User user = userService.readById(5);

        mockMvc.perform(post("/users/{id}/update", 5)
                        .param("id", String.valueOf(5))
                        .queryParam("roleId", "5")
                        .flashAttr("user", user))
                        .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                        .andExpect(MockMvcResultMatchers.redirectedUrl("/users/" + 5 + "/read"));
    }
    @Test
    @Transactional
    public void readTest() throws Exception {
        User user = userService.readById(5);

        mockMvc.perform(get("/users/{id}/read", 5))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", user))
                .andExpect(MockMvcResultMatchers.view().name("user-info"));
    }
    @Test
    @Transactional
    public void deleteTest() throws Exception {
        mockMvc.perform(get("/users/{id}/delete", 5)
                .param("id", String.valueOf(5)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/users/all"));
    }
    @Test
    @Transactional
    public void getAllTest() throws Exception {
        List<User> users = userService.getAll();
        mockMvc.perform(get("/users/all"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.model().attribute("users", users))
                .andExpect(MockMvcResultMatchers.view().name("users-list"));
    }

}
