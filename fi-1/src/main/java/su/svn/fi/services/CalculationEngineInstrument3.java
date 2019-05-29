package su.svn.fi.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import su.svn.fi.models.Instrument;

@Service("calculationEngineInstrument3")
public class CalculationEngineInstrument3 implements CalculationEngine
{
    private double maximum = Double.MIN_VALUE;

    private double minimum = Double.MAX_VALUE;

    @Override
    public synchronized void apply(Instrument instrument)
    {
        if (maximum < instrument.getValue()) {
            maximum = instrument.getValue();
        }
        if (minimum > instrument.getValue()) {
            minimum = instrument.getValue();
        }
    }

    @Override
    public double getResult()
    {
        return maximum - minimum;
    }
}
