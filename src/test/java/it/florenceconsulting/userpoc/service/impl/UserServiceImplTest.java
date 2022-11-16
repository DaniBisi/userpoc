/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.florenceconsulting.userpoc.service.impl;

import it.florenceconsulting.userpoc.dao.UserDao;
import it.florenceconsulting.userpoc.dto.UserDto;
import it.florenceconsulting.userpoc.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author daniele
 */
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl instance;
    @Mock
    private UserDao userDao;

    public UserServiceImplTest() {
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

    @Test
    public void testFromDto() {
        System.out.println("fromDto");
        Long id = 1L;
        User u = User.builder().id(1L)
                .cellphone("333333333")
                .email("test@email.com")
                .firstname("testname")
                .lastname("testLastname")
                .username("testUsername").build();
        when(userDao.getById(id)).thenReturn(u);
        String newCellphone = "22222222";
        UserDto userDto = UserDto.builder()
                .id(id)
                .cellphone(newCellphone)
                .email("test@email.com")
                .firstname("testname")
                .lastname("testLastname")
                .username("testUsername").build();
        User result = instance.fromDto(userDto);
        assertNotNull(result);
        assertEquals(newCellphone, result.getCellphone());
        /*
        this shoud test every field that has to be merged
         */
    }

    /**
     * Test of fromModel method, of class UserServiceImpl.
     */
    @Test
    public void testFromModel() {
        System.out.println("fromModel");
        Long id = 1L;
        User u = User.builder().id(1L)
                .cellphone("333333333")
                .email("test@email.com")
                .firstname("testname")
                .lastname("testLastname")
                .username("testUsername").build();
        when(userDao.getById(id)).thenReturn(u);
        UserDto result = instance.fromModel(u);
        assertNotNull(result);
        assertEquals(u.getCellphone(), result.getCellphone());
        /*
        this shoud test every field that has to be merged
         */

    }

}
