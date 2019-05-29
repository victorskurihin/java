package su.svn.fi.services;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.function.Function;

public class SingletonFunctionBean
{
    @Autowired
    private Function<String, CalculationEngineOther> beanFactory;

    public CalculationEngineOther getPrototypeInstance(String name) {
        return beanFactory.apply(name);
    }
}
