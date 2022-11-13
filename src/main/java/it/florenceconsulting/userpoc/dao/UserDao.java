/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.florenceconsulting.userpoc.dao;

import it.florenceconsulting.userpoc.models.User;
import java.util.List;

/**
 *
 * @author daniele
 */
public interface UserDao extends GenericDao<User, Long> {

    public List<User> getByParams(String username, String lastname, String firstname, Boolean allFilters);

    
}
