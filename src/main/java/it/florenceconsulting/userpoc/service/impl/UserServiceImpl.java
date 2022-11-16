/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.florenceconsulting.userpoc.service.impl;

import it.florenceconsulting.userpoc.dao.UserDao;
import it.florenceconsulting.userpoc.dto.UserDto;
import it.florenceconsulting.userpoc.models.User;
import it.florenceconsulting.userpoc.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User fromDto(UserDto userDto) {

        if (userDto == null) {
            return null;
        }
        User user = null;
        if (userDto.getId() != null) {
            user = userDao.getById(userDto.getId());
        }
        if (user == null) {
            user = User.builder().build();
        }
        user.setCellphone(userDto.getCellphone());
        user.setEmail(userDto.getEmail());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setUsername(userDto.getUsername());
        return user;

    }

    @Override
    public UserDto fromModel(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .cellphone(user.getCellphone()).build();
    }

}
