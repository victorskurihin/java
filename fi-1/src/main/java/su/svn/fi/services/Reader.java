package su.svn.fi.services;

import java.util.Map;
import java.util.function.Consumer;

public interface Reader
{
    void read(Consumer<Map<String, Long> > consumer);
}
