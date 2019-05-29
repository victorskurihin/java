package su.svn.fi.services;

import su.svn.fi.models.Instrument;

public interface CalculationEngine
{
    void apply(Instrument instrument);

    double getResult();
}
