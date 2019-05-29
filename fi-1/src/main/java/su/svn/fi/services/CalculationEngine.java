package su.svn.fi.services;

public interface CalculationEngine<T extends Number>
{
    void apply(String line);
}
