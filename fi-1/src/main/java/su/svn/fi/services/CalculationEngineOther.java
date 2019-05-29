package su.svn.fi.services;

import su.svn.fi.models.Instrument;

import java.util.ArrayDeque;
import java.util.Deque;

public class CalculationEngineOther implements CalculationEngine
{
    private final String name;

    private Deque<Double> deque = new ArrayDeque<>();

    public CalculationEngineOther(String name)
    {
        this.name = name;
    }

    @Override
    public synchronized void apply(Instrument instrument)
    {
        deque.addLast(instrument.getValue());
        if (deque.size() > 10) {
            deque.removeFirst();
        }
    }

    @Override
    public double getResult()
    {
        return deque.stream().reduce(0.0, (d1, d2) -> d1 + d2);
    }
}
