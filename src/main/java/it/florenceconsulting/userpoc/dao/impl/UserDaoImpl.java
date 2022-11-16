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
import java.util.stream.Collectors;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
            hql = "from User u where u.username LIKE :USERNAME AND u.lastname LIKE :LASTNAME AND u.firstname LIKE :FIRSTNAME";
        } else {
            hql = "from User u where u.username LIKE :USERNAME OR u.lastname LIKE :LASTNAME OR u.firstname LIKE :FIRSTNAME";
        }
        Query query = createQuery(hql);
        query.setParameter("USERNAME", "%" + username + "%");
        query.setParameter("LASTNAME", "%" + lastname + "%");
        query.setParameter("FIRSTNAME", "%" + firstname + "%");

        List resultList = query.getResultList();
        return resultList;
    }

    @Override
    @Transactional(propagation = Propagation.NESTED,
            isolation = Isolation.READ_UNCOMMITTED)
    public List<User> saveAll(List<User> usersToBeSaved) {
        return usersToBeSaved.stream().map((entity) -> {
            return this.save(entity);
        }
        ).collect(Collectors.toList());
    }

}
