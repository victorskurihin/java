package ru.otus.mainPackage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.mainPackage.configs.YamlProps;


@RestController
@SpringBootApplication
@EnableConfigurationProperties(YamlProps.class)
//@EnableAutoConfiguration - не увидит Preparation Component
public class Demo {

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Demo.class, args);
    }
}
