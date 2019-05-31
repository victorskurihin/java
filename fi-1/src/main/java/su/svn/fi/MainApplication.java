package su.svn.fi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import su.svn.fi.configs.YamlApplicationProperties;

@SpringBootApplication
@EnableConfigurationProperties(YamlApplicationProperties.class)
public class MainApplication
{
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
}
