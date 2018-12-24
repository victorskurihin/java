package ru.otus.mainPackage.сommandLineRunners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
@Profile("prod")
public class PreparationProd implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(PreparationProd.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info("PROD mode!!! Что-то настравиваем и подготавливаем, параметры: {} ", Arrays.toString(args));
        //args парметры, котрые могут быть переданы в Main
    }
}
