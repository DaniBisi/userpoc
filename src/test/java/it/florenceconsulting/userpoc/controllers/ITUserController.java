/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.florenceconsulting.userpoc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.florenceconsulting.userpoc.config.InMemoryConfig;
import it.florenceconsulting.userpoc.dto.UserDto;
import it.florenceconsulting.userpoc.models.User;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author daniele
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {InMemoryConfig.class})
@WebAppConfiguration
@TestPropertySource(
        locations = {"classpath:application.properties", "classpath:persistence-h2.properties"})
public class ITUserController {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private ObjectMapper objectMapper;

    private MockMvc mvc;

    public ITUserController() {
        this.objectMapper = new ObjectMapper();
    }

    @BeforeEach
    void printApplicationContext() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Arrays.stream(webApplicationContext.getBeanDefinitionNames())
                .map(name -> webApplicationContext.getBean(name).getClass().getName())
                .sorted()
                .forEach(System.out::println);
    }

    /**
     * Test of updateUser method, of class UserController.
     */
    @Test
    public void testUpdateUser() throws Exception {
        System.out.println("updateUser");
        Long id = 1L;
        mvc.perform(get(UserController.APIUSERSID, id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("Daniele"));

    }

    /**
     * Test of updateUser method, of class UserController.
     */
    @Test
    public void testValidationError() throws Exception {
        System.out.println("updateUser");
        UserDto user = UserDto.builder()
                .id(null)
                .cellphone("333333333")
                .email("test@email.com")
                .firstname("testname")
                .lastname("testLastname")
                .username("testUsername").build();

        MvcResult returnValue = mvc.perform(post(UserController.APIUSERSADD)
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("testname")).andReturn();

        UserDto readValue = objectMapper.readValue(returnValue.getResponse().getContentAsString(), UserDto.class);
        assertNotNull(readValue);

    }

    @Test
    public void testCreateUser() throws Exception {
        System.out.println("updateUser");

        mvc.perform(get(UserController.APIUSERS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("bob"));

    }

    /**
     * Test of updateUser method, of class UserController.
     */
    @Test
    public void testCreateUserValidationError() throws Exception {
        System.out.println("updateUser");
        UserDto user = UserDto.builder()
                .id(1L)
                .cellphone("333333333")
                .email("test@email.com")
                .firstname("testname")
                .lastname("testLastname")
                .username("testUsername").build();

        mvc.perform(get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstname").value("Daniele"));

    }

    /**
     * Test of getUser method, of class UserController.
     */
    @Test
    public void testGetUser() throws Exception {
        System.out.println("getUser");
        Long id = 1L;
        User u = User.builder().id(1L)
                .cellphone("333333333")
                .email("test@email.com")
                .firstname("testname")
                .lastname("testLastname")
                .username("testUsername").build();

        mvc.perform(get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("bob"));
    }

    @Test
    public void testGetUserNotPresentInDbNull() throws Exception {
        System.out.println("getUser");
        Long id = 1L;

        mvc.perform(get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("bob"));
    }

    /**
     * Test of deleteUser method, of class UserController.
     */
    @Test
    public void testDeleteUser() throws Exception {
        System.out.println("deleteUser");
        Long id = 1L;

        mvc.perform(get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("bob"));
    }

    /**
     * Test of getAllUsers method, of class UserController.
     */
    @Test
    public void testGetAllUsers() throws Exception {
        System.out.println("getAllUsers");
        List<User> lu = new ArrayList();
        User u = User.builder().id(1L)
                .cellphone("333333333")
                .email("test@email.com")
                .firstname("testname")
                .lastname("testLastname")
                .username("testUsername").build();
        User u2 = User.builder().id(2L)
                .cellphone("333333333")
                .email("test2@email.com")
                .firstname("testname2")
                .lastname("testLastname2")
                .username("testUsername2").build();
        lu.add(u2);
        lu.add(u);

        mvc.perform(get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("bob"));

    }

    /**
     * Test of getAllUsers method, of class UserController.
     */
    @Test
    public void testSearch() throws Exception {
        System.out.println("getAllUsers");
        String username = "testUsername";
        String lastname = "testlastname";
        String firstname = "testfirstname";
        Boolean allFilters = Boolean.TRUE;
        List<User> lu = new ArrayList();
        User u = User.builder().id(1L)
                .cellphone("333333333")
                .email("test@email.com")
                .firstname("testname")
                .lastname("testLastname")
                .username("testUsername").build();
        User u2 = User.builder().id(2L)
                .cellphone("333333333")
                .email("test2@email.com")
                .firstname("testname2")
                .lastname("testLastname2")
                .username("testUsername2").build();
        lu.add(u2);
        lu.add(u);

        mvc.perform(get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("bob"));
    }

    /**
     * Test of upload method, of class UserController.
     */
    @Test
    public void testUpload() throws Exception {
        System.out.println("upload");
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.csv");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("db.csv", "some_name", "text/csv", inputStream);

        mvc.perform(get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("bob"));
    }

}
