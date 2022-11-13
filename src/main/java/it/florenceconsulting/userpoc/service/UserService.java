/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.florenceconsulting.userpoc.service;

import it.florenceconsulting.userpoc.dto.UserDto;
import it.florenceconsulting.userpoc.models.User;

/**
 *
 * @author daniele
 */
public interface UserService {

    public User fromDto(UserDto user);

    public UserDto fromModel(User byId);
    
}
