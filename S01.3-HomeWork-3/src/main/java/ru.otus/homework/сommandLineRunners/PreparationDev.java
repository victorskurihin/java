package ru.otus.homework.сommandLineRunners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
@Profile("dev")
public class PreparationDev implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(PreparationDev.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info("DEV mode!!! Что-то настравиваем и подготавливаем, параметры: {} ", Arrays.toString(args));
        //args парметры, котрые могут быть переданы в Main
    }
}
