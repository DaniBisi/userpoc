package it.florenceconsulting.userpoc;

import it.florenceconsulting.userpoc.config.BeanTestConfig;
import it.florenceconsulting.userpoc.config.InMemoryConfig;
import it.florenceconsulting.userpoc.dao.UserDao;
import it.florenceconsulting.userpoc.models.User;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {InMemoryConfig.class,BeanTestConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class ITUserDao {

    @Autowired
    private UserDao userDao;

    @Test
    public void getById() {

        User user = userDao.getById(1L);
        assertNotNull(user);
    }
}
