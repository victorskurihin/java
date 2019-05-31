package su.svn.fi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class Instrument
{
    private String name;

    private long number;

    private LocalDate date;

    private double value;
}
