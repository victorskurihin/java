package ru.otus.l092;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@SpringBootApplication
public class L092mybatisApplication implements CommandLineRunner {

    CityMapper cityMapper;

    public L092mybatisApplication(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

	public static void main(String[] args) {
		System.out.println(
            "Before init, args = " +
            Arrays.stream(args).collect(Collectors.toList()) +
            " ..."
        );
		SpringApplication.run(L092mybatisApplication.class, args);
		System.out.println("After init.");
	}

	@Override
	public void run(String... strings) throws Exception {
		System.out.println(
		    "Hello!. strings = " + Arrays.stream(strings).collect(Collectors.toList())
        );
        System.out.println("cityMapper = " + cityMapper.findCityByState("CA"));
        System.out.println("cityMapper = " + cityMapper.findCityByCountry("РФ"));
	}
}
