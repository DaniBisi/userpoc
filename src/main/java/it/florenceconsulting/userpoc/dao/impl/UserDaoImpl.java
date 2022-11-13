/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.florenceconsulting.userpoc.dao.impl;

import org.springframework.stereotype.Repository;
import it.florenceconsulting.userpoc.dao.GenericDaoHibernate;
import it.florenceconsulting.userpoc.dao.UserDao;
import it.florenceconsulting.userpoc.models.User;

/**
 *
 * @author daniele
 */
@Repository
public class UserDaoImpl extends GenericDaoHibernate<User, Long> implements UserDao {

}
