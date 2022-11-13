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
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private FileService fileService;

    @RequestMapping(method = RequestMethod.PUT, value = "/api/users/{id}")
    ResponseEntity updateUser(@RequestBody @Valid UserDto user, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException();
        }
        User reg = userService.fromDto(user);
        User save = userDao.save(reg);
        return ResponseEntity.ok(save);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/users/add")
    ResponseEntity createUser(@RequestBody @Valid UserDto user, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException();
        }
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

    @RequestMapping(method = RequestMethod.GET, value = "/api/users/search")
    ResponseEntity getAllUsers(@RequestParam(required = false) String username, @RequestParam(required = false) String lastname, @RequestParam(required = false) String firstname, @RequestParam(required = true) Boolean allFilters) {
        return ResponseEntity.ok(userDao.getByParams(username, lastname, firstname, allFilters).stream().map((t)
                -> userService.fromModel(t)
        ).collect(Collectors.toList()));

    }

    @RequestMapping(value = "/uploadCsv", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity upload(@RequestParam("file") MultipartFile file, ModelMap modelMap) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        String contentType = file.getContentType();
        if (!"text/csv".equals(contentType)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Wrong file's content type");
        }
        List<UserDto> loaded = fileService.loadFromCsv(file);
        List<UserDto> collect = loaded.stream().map((userDto) -> userService.fromDto(userDto)).flatMap(Stream::of).map((user) -> userService.fromModel(userDao.save(user))).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }

}
