package su.svn.fi.services;

import org.springframework.stereotype.Service;
import su.svn.fi.models.Instrument;

import java.time.LocalDate;
import java.time.Month;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;

@Service("calculationEngineMeanForNovember2014")
public class CalculationEngineMeanForNovember2014 implements CalculationEngine
{
    private DoubleAdder sum = new DoubleAdder();

    private LongAdder count = new LongAdder();

    boolean checkDay(LocalDate date)
    {
        return date.getYear() == 2014 && date.getMonth() == Month.NOVEMBER;
    }

    @Override
    public void apply(Instrument instrument)
    {
        if (checkDay(instrument.getDate())) {
            sum.add(instrument.getValue());
            count.increment();
        }
    }

    @Override
    public double getResult()
    {
        return sum.doubleValue() / count.longValue();
    }
}
