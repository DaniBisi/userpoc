/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.florenceconsulting.userpoc.controllers;

import it.florenceconsulting.userpoc.dao.UserDao;
import it.florenceconsulting.userpoc.dto.UserDto;
import it.florenceconsulting.userpoc.models.User;
import it.florenceconsulting.userpoc.service.FileService;
import it.florenceconsulting.userpoc.service.UserService;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BindingResult;

/**
 *
 * @author daniele
 */
public class UserControllerTest {

    @InjectMocks
    private UserController instance;

    @Mock
    UserDao userDao;
    @Mock
    private UserService userService;
    @Mock
    private FileService fileService;

    public UserControllerTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of updateUser method, of class UserController.
     */
    @Test
    public void testUpdateUser() {
        System.out.println("updateUser");
        UserDto user = UserDto.builder()
                .id(1L)
                .cellPhone("333333333")
                .email("test@email.com")
                .firstName("testname")
                .lastName("testLastname")
                .userName("testUsername").build();
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(Boolean.FALSE);

        instance.updateUser(user, binding);
        verify(userService, times(1)).fromDto(user);
        verify(userDao, times(1)).save(ArgumentMatchers.any());

    }

    /**
     * Test of updateUser method, of class UserController.
     */
    @Test
    public void testValidationError() {
        System.out.println("updateUser");
        UserDto user = UserDto.builder()
                .id(1L)
                .cellPhone("333333333")
                .email("test@email.com")
                .firstName("testname")
                .lastName("testLastname")
                .userName("testUsername").build();
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(Boolean.TRUE);
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            instance.updateUser(user, binding);
        });
        assertNotNull(thrown);

    }

    @Test
    public void testCreateUser() {
        System.out.println("updateUser");
        UserDto user = UserDto.builder()
                .id(1L)
                .cellPhone("333333333")
                .email("test@email.com")
                .firstName("testname")
                .lastName("testLastname")
                .userName("testUsername").build();
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(Boolean.FALSE);

        instance.createUser(user, binding);
        verify(userService, times(1)).fromDto(user);
        verify(userDao, times(1)).save(ArgumentMatchers.any());

    }

    /**
     * Test of updateUser method, of class UserController.
     */
    @Test
    public void testCreateUserValidationError() {
        System.out.println("updateUser");
        UserDto user = UserDto.builder()
                .id(1L)
                .cellPhone("333333333")
                .email("test@email.com")
                .firstName("testname")
                .lastName("testLastname")
                .userName("testUsername").build();
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(Boolean.TRUE);
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            instance.updateUser(user, binding);
        });
        assertNotNull(thrown);

    }

    /**
     * Test of getUser method, of class UserController.
     */
    @Test
    public void testGetUser() {
        System.out.println("getUser");
        Long id = 1L;
        User u = User.builder().id(1L)
                .cellPhone("333333333")
                .email("test@email.com")
                .firstName("testname")
                .lastName("testLastname")
                .userName("testUsername").build();
        when(userDao.getById(id)).thenReturn(u);
        instance.getUser(id);
        verify(userDao, times(1)).getById(id);
        verify(userService, times(1)).fromModel(u);
    }

    @Test
    public void testGetUserNotPresentInDbNull() {
        System.out.println("getUser");
        Long id = 1L;
        when(userDao.getById(id)).thenReturn(null);
        instance.getUser(id);
        verify(userDao, times(1)).getById(id);
        verify(userService, times(1)).fromModel(null);
    }

    /**
     * Test of deleteUser method, of class UserController.
     */
    @Test
    public void testDeleteUser() {
        System.out.println("deleteUser");
        Long id = 1L;
        instance.deleteUser(id);
        verify(userDao, times(1)).deleteById(id);
    }

    /**
     * Test of getAllUsers method, of class UserController.
     */
    @Test
    public void testGetAllUsers() {
        System.out.println("getAllUsers");
        List<User> lu = new ArrayList();
        User u = User.builder().id(1L)
                .cellPhone("333333333")
                .email("test@email.com")
                .firstName("testname")
                .lastName("testLastname")
                .userName("testUsername").build();
        User u2 = User.builder().id(2L)
                .cellPhone("333333333")
                .email("test2@email.com")
                .firstName("testname2")
                .lastName("testLastname2")
                .userName("testUsername2").build();
        lu.add(u2);
        lu.add(u);
        when(userDao.getAll()).thenReturn(lu);
        instance.getAllUsers();
        verify(userDao, times(1)).getAll();
        verify(userService, times(2)).fromModel(ArgumentMatchers.any());

    }

    /**
     * Test of getAllUsers method, of class UserController.
     */
    @Test
    public void testSearch() {
        System.out.println("getAllUsers");
        String username = "testUsername";
        String lastname = "testlastname";
        String firstname = "testfirstname";
        Boolean allFilters = Boolean.TRUE;
        List<User> lu = new ArrayList();
        User u = User.builder().id(1L)
                .cellPhone("333333333")
                .email("test@email.com")
                .firstName("testname")
                .lastName("testLastname")
                .userName("testUsername").build();
        User u2 = User.builder().id(2L)
                .cellPhone("333333333")
                .email("test2@email.com")
                .firstName("testname2")
                .lastName("testLastname2")
                .userName("testUsername2").build();
        lu.add(u2);
        lu.add(u);
        when(userDao.getByParams(username, lastname, firstname, allFilters)).thenReturn(lu);
        instance.search(username, lastname, firstname, allFilters);
        verify(userDao, times(1)).getByParams(username, lastname, firstname, allFilters);
        verify(userService, times(2)).fromModel(ArgumentMatchers.any());
    }

    /**
     * Test of upload method, of class UserController.
     */
    @Test
    public void testUpload() throws Exception {
        System.out.println("upload");
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.csv");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("db.csv", "some_name", "text/csv", inputStream);
        instance.upload(mockMultipartFile);
        verify(fileService, times(1)).loadFromCsv(mockMultipartFile);
        verify(userDao, times(1)).saveAll(ArgumentMatchers.any());
    }

}
