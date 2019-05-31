package su.svn.fi.services;

import su.svn.fi.models.Instrument;

public interface Parser
{
    Instrument parse(String line);

    Instrument parse(String line, long number);
}
