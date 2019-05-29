package su.svn.fi.services;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
public class WorkDayChecker implements DayChecker
{
    public static LocalDate LAST_DAY = LocalDate.of(2014, 12, 19);

    @Override
    public boolean isValid(LocalDate date)
    {
        return date.isBefore(LAST_DAY) && date.getDayOfWeek().getValue() < DayOfWeek.SATURDAY.getValue();
    }
}
