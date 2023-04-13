package uz.internal_affairs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DepartmentOfInternalAffairsApplication {
    public static void main(String[] args) {
        SpringApplication.run(DepartmentOfInternalAffairsApplication.class, args);
    }
}
