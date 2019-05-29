package su.svn.fi.services;

import org.springframework.stereotype.Service;

@Service("instrumentMean")
public class CalculationEngineMean implements CalculationEngine<Double>
{
    private double mean = 0;

    private long count = 0L;

    @Override
    public void apply(String line)
    {
        // TODO
    }
}
