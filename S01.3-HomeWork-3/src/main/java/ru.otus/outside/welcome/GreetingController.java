package ru.otus.outside.welcome;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.configs.YamlProps;

import java.util.Map;

// @RestController
public class GreetingController {
    private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);
    private final Greeting greeting;

    public GreetingController(Greeting greeting, YamlProps props) {
        this.greeting = greeting;
        logger.info("ATTENTION! props.getMessage(): {}", props.getMessage());
    }

    //http://localhost:8082/hello?name=ddd
//    @GetMapping("/hello")
//    @ResponseBody
//    public Map<String, String> sayHello(@RequestParam(name="name") String name) {
//        return this.greeting.sayHello(name);
//    }
}
