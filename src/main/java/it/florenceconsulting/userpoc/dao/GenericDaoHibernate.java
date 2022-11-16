/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.florenceconsulting.userpoc.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class GenericDaoHibernate<T, ID extends Serializable> implements GenericDao<T, ID> {

    private Class<T> persistentClass;

    @Autowired
    public LocalSessionFactoryBean sessionFactory;

    public GenericDaoHibernate() {
        Class c = getClass();
        ParameterizedType genericSuperclass = (ParameterizedType) c.getGenericSuperclass();
        Type[] argsTypes = genericSuperclass.getActualTypeArguments();
        Type tipo = argsTypes[0];
        this.persistentClass = (Class<T>) tipo;
    }

    protected Class<T> getPersistentClass() {
        return persistentClass;
    }

    protected Session getSession() {
        return sessionFactory.getObject().getCurrentSession();
    }

    protected Criteria createCriteria() {
        return getSession().createCriteria(getPersistentClass());
    }

    protected Query createQuery(String hql) {
        return getSession().createQuery(hql);
    }

    @Override
    public int count() {
        Object obj = getSession().createQuery("select count(*) from " + getPersistentClass().getName()).list().get(0);
        Integer count = (Integer) obj;

        return count;
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(T entity) {
        getSession().delete(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(ID... ids) {
        Criteria c = createCriteria();
        c.add(Restrictions.in("id", ids));
        for (Object entity : c.list()) {
            getSession().delete(entity);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(ID id) {
        T t = getById(id);
        if (t != null) {
            getSession().delete(t);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteEntities(T... entities) {
        for (T entity : entities) {
            if (entity != null) {
                getSession().delete(entity);
            }
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteEntity(T entity) {
        getSession().delete(entity);
    }

    @Override
    public boolean exists(ID id) {
        Criteria c = createCriteria();
        c.add(Restrictions.eq("id", id));

        return c.list().size() == 1;
    }

    /**
     * Use this inside subclasses as a convenience method.
     */
    protected List<T> findByCriteria(Criterion... criterions) {
        Criteria crit = getSession().createCriteria(getPersistentClass());
        for (Criterion c : criterions) {
            crit.add(c);
        }
        return crit.list();
    }

    protected List<T> findByCriteria(List<Criterion> criterions) {
        Criteria crit = getSession().createCriteria(getPersistentClass());
        for (Criterion c : criterions) {
            crit.add(c);
        }
        return crit.list();
    }

    /**
     * Convenience method to order a query, and apply some Criterions.<br>
     * You can create an Order with Order.asc(<i>propertyname</i>) and
     * Order.desc(<i>propertyname</i>)<br>
     * You can create Criterions with Restrictions static methods.
     *
     * @param order order to apply
     * @param criterions criterions to add
     * @return the query result
     */
    protected List<T> findByCriteria(Order order, Criterion... criterions) {
        Criteria crit = getSession().createCriteria(getPersistentClass()).addOrder(order);
        for (Criterion c : criterions) {
            crit.add(c);
        }
        return crit.list();
    }

    protected List<T> findByQuery(String hql, Object... values) {
        Query query = getSession().createQuery(hql);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        return query.list();
    }

    /**
     * Use this inside subclasses as a convenience method.
     */
    protected T findUniqueByCriteria(Criterion... criterions) {
        Criteria crit = getSession().createCriteria(getPersistentClass());
        for (Criterion c : criterions) {
            crit.add(c);
        }
        List<T> list = crit.list();
        if (list == null || list.size() < 1) {
            // log.warn( "Numero di risultati != 1." );
            return null;
        }
        return list.get(0);
    }

    protected T findUniqueByCriteria(List<Criterion> criterions) {
        Criteria crit = getSession().createCriteria(getPersistentClass());
        for (Criterion c : criterions) {
            crit.add(c);
        }
        List<T> list = crit.list();
        if (list == null || list.size() < 1) {
            // log.warn( "Numero di risultati != 1." );
            return null;
        }
        return list.get(0);
    }

    protected T findUniqueByQuery(String hql, Object... values) {
        Query query = getSession().createQuery(hql);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        List<T> list = query.list();
        if (list == null || list.size() < 1) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<T> getAll() {
        return findByCriteria();
    }

    @Override
    public List<T> getAll(int maxResult) {
        Criteria criteria = createCriteria().setMaxResults(maxResult);

        return criteria.list();
    }

    @Override
    public List<T> getAll(int start, int size) {
        String hql = " from " + getPersistentClass().getName();
        Query query = getSession().createQuery(hql);
        query.setFirstResult(start);
        query.setMaxResults(size);

        return query.list();
    }

    @Override
    public List<T> getAllSortedByField(String field, boolean asc) {
        Criteria crit = getSession().createCriteria(getPersistentClass());
        if (asc) {
            crit.addOrder(Order.asc(field));
        } else {
            crit.addOrder(Order.desc(field));
        }

        return crit.list();
    }

    @Override
    public List<T> getById(Collection<ID> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<T>();
        }
        Criteria c = createCriteria();
        if (ids != null) {
            c.add(Restrictions.in("id", ids));
        }

        return c.list();
    }

    @Override
    public T getById(ID id) {
        if (id != null) {
            T entity = (T) getSession().get(getPersistentClass(), id);
            return entity;
        } else {
            return null;
        }
    }

    @Override
    public List<T> getById(ID... ids) {
        if (ids.length == 0) {
            return null;
        }
        Criteria c = createCriteria();
        c.add(Restrictions.in("id", ids));

        return c.list();
    }

    @Override
    @Transactional(readOnly = false)
    public T merge(T entity) {
        getSession().merge(entity);
        return entity;
    }

    @Override
    @Transactional(readOnly = false)
    public T save(T entity) {
        getSession().saveOrUpdate(entity);
        return entity;
    }

    @Override
    @Transactional(readOnly = false)
    public T update(T entity) {
        getSession().update(entity);
        return entity;
    }

}
/*
.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
*/