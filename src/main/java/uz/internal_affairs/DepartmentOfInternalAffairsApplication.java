package uz.internal_affairs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EntityScan(value = {"uz.internal_affairs.entity"})
//@EnableJpaRepositories(value = {"uz.internal_affairs.repository"})
@SpringBootApplication
public class DepartmentOfInternalAffairsApplication {
    public static void main(String[] args) {
        SpringApplication.run(DepartmentOfInternalAffairsApplication.class, args);
    }
}
