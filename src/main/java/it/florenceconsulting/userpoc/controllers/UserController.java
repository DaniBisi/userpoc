/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.florenceconsulting.userpoc.controllers;

import it.florenceconsulting.userpoc.dao.UserDao;
import it.florenceconsulting.userpoc.dto.UserDto;
import it.florenceconsulting.userpoc.models.User;
import it.florenceconsulting.userpoc.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author daniele
 */
@RestController
public class UserController {

    @Autowired
    UserDao userDao;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.PUT, value = "/api/users/{id}")
    ResponseEntity updateUser(@RequestBody UserDto user) {
        User reg = userService.fromDto(user);
        User save = userDao.save(reg);
        return ResponseEntity.ok(save);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/users/add")
    ResponseEntity createUser(@RequestBody UserDto user) {
        User reg = userService.fromDto(user);
        User save = userDao.save(reg);
        return ResponseEntity.ok(save);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/users/{id}")
    ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        UserDto regDto = userService.fromModel(userDao.getById(id));
        return ResponseEntity.ok(regDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/api/users/{id}/delete")
    ResponseEntity deleteUser(@PathVariable("id") Long id) {
        userDao.deleteById(id);
        return ResponseEntity.ok().build();

    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/users")
    ResponseEntity getAllUsers() {
        List<User> all = userDao.getAll();
        List<UserDto> collectUsers = all.stream().map((t) -> {
            return userService.fromModel(t);
        }).collect(Collectors.toList());
        return ResponseEntity.ok(collectUsers);

    }
}
