package it.florenceconsulting.userpoc.config;

import it.florenceconsulting.userpoc.dao.UserDao;
import it.florenceconsulting.userpoc.dao.impl.UserDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 *
 * @author daniele
 */
@Configuration
public class BeanTestConfig {

    @Autowired
    private Environment env;

    @Bean
    public UserDao userDao() {
        return new UserDaoImpl();

    }

}
