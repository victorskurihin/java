package su.svn.fi.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Instrument
{
    private String instrumentName;

    private LocalDate date;

    private double value;
}
