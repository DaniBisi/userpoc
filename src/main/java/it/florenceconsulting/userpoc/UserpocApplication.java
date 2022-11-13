package it.florenceconsulting.userpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@EnableSwagger2
public class UserpocApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserpocApplication.class, args);
    }

}
