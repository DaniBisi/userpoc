package it.florenceconsulting.userpoc.dao;

import it.florenceconsulting.userpoc.config.InMemoryConfig;
import it.florenceconsulting.userpoc.models.User;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {InMemoryConfig.class})
@WebAppConfiguration
@TestPropertySource(
        locations = {"classpath:application.properties", "classpath:persistence-h2.properties"})
public class UserDaoIT {

    @Autowired
    private UserDao userDao;

    @Test
    public void getById() {

        User user = userDao.getById(1L);
        assertNotNull(user);
    }
}
