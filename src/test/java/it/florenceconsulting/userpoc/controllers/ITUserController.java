/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.florenceconsulting.userpoc.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.florenceconsulting.userpoc.config.InMemoryConfig;
import it.florenceconsulting.userpoc.dto.UserDto;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void testValidationError() throws Exception {
        System.out.println("updateUser");
        UserDto user = UserDto.builder()
                .id(null)
                .cellphone("333333333")
                .email("test@email.com")
                .firstname("test-àname")
                .lastname("testLastname")
                .username("testUsername").build();

        mvc.perform(post(UserController.APIUSERSADD)
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void testCreateUser() throws Exception {
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

    /**
     * Test of getUser method, of class UserController.
     */
    @Test
    public void testGetUser() throws Exception {
        System.out.println("getUser");
        Long id = 1L;

        mvc.perform(get(UserController.APIUSERSID, id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("Daniele"));
    }

    @Test
    public void testGetUserNotPresentInDbNull() throws Exception {
        System.out.println("getUser");
        Long id = 222L;

        MvcResult returnValue = mvc.perform(get(UserController.APIUSERSID, id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals("", returnValue.getResponse().getContentAsString());
    }

    /**
     * Test of deleteUser method, of class UserController.
     */
    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void testDeleteUser() throws Exception {
        System.out.println("deleteUser");
        Long id = 1L;

        mvc.perform(delete(UserController.APIUSERSID, id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        MvcResult returnValue = mvc.perform(get(UserController.APIUSERSID, id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals("", returnValue.getResponse().getContentAsString());
    }

    /**
     * Test of getAllUsers method, of class UserController.
     */
    @Test
    public void testGetAllUsers() throws Exception {
        System.out.println("getAllUsers");

        MvcResult returnValue = mvc.perform(get(UserController.APIUSERS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();
        List<UserDto> readValue = objectMapper.readValue(returnValue.getResponse().getContentAsString(), new TypeReference<List<UserDto>>() {
        });
        assertEquals(3, readValue.size());
    }

    /**
     * Test of getAllUsers method, of class UserController.
     */
    @Test
    public void testSearch() throws Exception {
        System.out.println("getAllUsers");
        String username = "Daniele";
        String lastname = "Bisignano";
        String firstname = "Daniele";

        /*
        	 (1,'3936554922','daniele.bisignano@gmail.com','Daniele','Bisignano','Daniele'),
	 (2,'3936554923','daniele.bisignano2@gmail.com','Daniele2','Bisignano2','Daniele2'),
	 (3,'3936554924','pippo@gmail.com','pippo','Pippo','pippo234');

         */
        MultiValueMap params = new LinkedMultiValueMap<String, String>();
        params.add("username", username);
        params.add("lastname", lastname);
        params.add("firstname", firstname);
        params.add("allFilters", Boolean.TRUE.toString());
        mvc.perform(get(UserController.APIUSERSSEARCH).params(params)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("Daniele"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].username").value("Daniele2"));
    }

    /**
     * Test of upload method, of class UserController.
     */
    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void testUpload() throws Exception {
        System.out.println("upload");
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.csv");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "db.csv", "text/csv", inputStream);

        MvcResult returnValue = mvc.perform(MockMvcRequestBuilders.multipart(UserController.UPLOAD_CSV)
                .file(mockMultipartFile))
                .andExpect(status().isOk()).andReturn();
        List<UserDto> readValue = objectMapper.readValue(returnValue.getResponse().getContentAsString(), new TypeReference<List<UserDto>>() {
        });
        assertEquals(22, readValue.size());
    }
    

}
