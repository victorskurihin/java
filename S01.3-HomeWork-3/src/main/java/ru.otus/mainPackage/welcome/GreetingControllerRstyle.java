package ru.otus.mainPackage.welcome;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class GreetingControllerRstyle {
    private static final Logger logger = LoggerFactory.getLogger(GreetingControllerRstyle.class);

    private final Greeting greeting;

    public GreetingControllerRstyle(Greeting greeting) {
        logger.info("Я НАСТОЯЩИЙ БИН!!!");
        this.greeting = greeting;
    }

    //http://localhost:8082/hello/jone
    @RequestMapping(method = RequestMethod.GET, value="/hello/{name}")
    @ResponseBody
    public Map<String, String> sayHello(@PathVariable("name") String name) {
        return this.greeting.sayHello(name);
    }
}
