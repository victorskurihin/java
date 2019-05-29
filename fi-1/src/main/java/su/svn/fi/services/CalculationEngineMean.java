package su.svn.fi.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import su.svn.fi.models.Instrument;

import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;

@Service("calculationEngineMean")
public class CalculationEngineMean implements CalculationEngine
{
    private DoubleAdder sum = new DoubleAdder();

    private LongAdder count = new LongAdder();

    @Override
    public void apply(Instrument instrument)
    {
        sum.add(instrument.getValue());
        count.increment();
    }

    @Override
    public double getResult()
    {
        return sum.doubleValue() / count.longValue();
    }
}
