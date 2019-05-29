package su.svn.fi.services;

import java.util.List;
import java.util.function.Consumer;

public interface Reader
{
    void read(Consumer<List<String> > consumer);
}
