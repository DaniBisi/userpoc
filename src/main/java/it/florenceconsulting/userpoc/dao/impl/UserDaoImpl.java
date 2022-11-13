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
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;

/**
 *
 * @author daniele
 */
@Repository
public class UserDaoImpl extends GenericDaoHibernate<User, Long> implements UserDao {

    @Override
    public List<User> getByParams(String username, String lastname, String firstname, Boolean allFilters) {
        String hql = null;
        if (allFilters) {
            hql = "from User u where u.userName LIKE :USERNAME AND u.lastName LIKE :LASTNAME AND u.firstName LIKE :FIRSTNAME";
        }else{
            hql = "from User u where u.userName LIKE :USERNAME OR u.lastName LIKE :LASTNAME OR u.firstName LIKE :FIRSTNAME";
        }
        Query query = createQuery(hql);
        query.setParameter("USERNAME", "%" + username + "%");
        query.setParameter("LASTNAME", "%" + lastname + "%");
        query.setParameter("FIRSTNAME", "%" + firstname + "%");

        List resultList = query.getResultList();
        return resultList;
    }

}
