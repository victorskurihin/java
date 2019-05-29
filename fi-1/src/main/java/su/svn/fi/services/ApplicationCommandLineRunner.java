package su.svn.fi.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import su.svn.fi.models.Instrument;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class ApplicationCommandLineRunner implements CommandLineRunner
{
    private static final Log LOG = LogFactory.getLog(ApplicationCommandLineRunner.class);

    private final Reader reader;
    private final Parser parser;
    private final DayChecker dayChecker;
    private final SingletonFunctionBean singletonFunctionBean;
    private final Map<String, CalculationEngine> engines = new HashMap<>();

    @Autowired
    public ApplicationCommandLineRunner(
        Reader reader,
        Parser parser,
        DayChecker dayChecker,
        @Qualifier("calculationEngineMean") CalculationEngine calculationEngineMean,
        @Qualifier("calculationEngineMeanForNovember2014") CalculationEngine calculationEngineMeanForNovember2014,
        @Qualifier("calculationEngineInstrument3") CalculationEngine calculationEngineInstrument3,
        SingletonFunctionBean singletonFunctionBean)
    {
        this.reader = reader;
        this.parser = parser;
        this.dayChecker = dayChecker;
        this.singletonFunctionBean = singletonFunctionBean;

        engines.put("INSTRUMENT1", calculationEngineMean);
        engines.put("INSTRUMENT2", calculationEngineMeanForNovember2014);
        engines.put("INSTRUMENT3", calculationEngineInstrument3);
    }

    private CalculationEngine getEngine(String name)
    {
        CalculationEngine engine = engines.get(name);
        if (Objects.isNull(engine)) {
            engine = singletonFunctionBean.getPrototypeInstance(name);
        }

        return engine;
    }

    @Override
    public void run(String... args) throws Exception
    {
        reader.read(strings -> strings.stream().parallel().forEach(line -> {
            Instrument instrument = parser.parse(line);
            if (dayChecker.isValid(instrument.getDate())) {
                CalculationEngine engine = getEngine(instrument.getName());
                engine.apply(instrument);
            }
        }));

        engines.forEach((instrumentName, calculationEngine) -> {
            System.out.println("Instrument name  = " + instrumentName);
            System.out.println("result = " + calculationEngine.getResult());
        });
    }
}
