package su.svn.fi.services;

import org.springframework.stereotype.Service;
import su.svn.fi.models.Instrument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class ParserImpl implements Parser
{
    boolean check(String[] elements)
    {
        // TODO with test
        return true;
    }

    LocalDate parseDate(String dateInString)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);

        return LocalDate.parse(dateInString, formatter);
    }

    @Override
    public Instrument parse(String line)
    {
        String elements[] = line.split(",");
        if ( ! check(elements)) {
            throw new RuntimeException("Unexpected input line " + line);
        }
        LocalDate date = parseDate(elements[1]);
        double value = Double.parseDouble(elements[0]);

        return new Instrument(elements[0], date, value);
    }
}
