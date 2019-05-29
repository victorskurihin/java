package su.svn.fi.services;

import java.time.LocalDate;

public interface DayChecker
{
    boolean isValid(LocalDate date);
}
