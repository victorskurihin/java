package su.svn.fi.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Instrument
{
    private String name;

    private LocalDate date;

    private double value;

    @Override
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if ( ! (o instanceof Instrument)) return false;
        Instrument other = (Instrument) o;
        if ( ! this.name.equals(other.name)) return false;
        if ( Math.abs(this.value - other.value) > Double.MIN_VALUE ) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        final int PRIME = 13;
        int result = 1;
        result = (result * PRIME) + this.name.hashCode();
        result = (result * PRIME) + this.date.hashCode();
        result = (result * PRIME) + new Double(value).hashCode();

        return result;
    }
}
