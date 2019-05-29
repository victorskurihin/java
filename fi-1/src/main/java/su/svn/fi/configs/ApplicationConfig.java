package su.svn.fi.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import su.svn.fi.services.CalculationEngineOther;
import su.svn.fi.services.SingletonFunctionBean;

import java.util.function.Function;

@Configuration
@ComponentScan
public class ApplicationConfig
{
    @Bean
    public Function<String, CalculationEngineOther> beanFactory() {
        return name -> prototypeBeanWithParam(name);
    }

    @Bean
    @Scope(value = "prototype")
    public CalculationEngineOther prototypeBeanWithParam(String name) {
        return new CalculationEngineOther(name);
    }

    @Bean
    public SingletonFunctionBean singletonFunctionBean() {
        return new SingletonFunctionBean();
    }
}
