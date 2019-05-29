package su.svn.fi.services;

import org.springframework.stereotype.Service;

@Service("instrument3")
public class CalculationEngineInstrument3 implements CalculationEngine<Double>
{
    private double result = 0;

    private long count = 0L;

    @Override
    public void apply(String line)
    {
        // TODO
    }
}
