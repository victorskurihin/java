package su.svn.fi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.svn.fi.configs.YamlApplicationProperties;

import java.util.*;
import java.util.function.Consumer;

@Service
public class ReaderStdIn implements Reader
{
    private int batchSize = 20;

    private Scanner console;

    private YamlApplicationProperties yap;

    ReaderStdIn(Scanner scanner)
    {
        console = scanner;
    }

    @Autowired
    public ReaderStdIn(YamlApplicationProperties yap)
    {
        this(new Scanner(System.in));
        batchSize = yap.getBatchSize();
    }

    @Override
    public void read(Consumer<Map<String, Long> > consumer)
    {
        long number = 0L;
        while (console.hasNextLine()) {
            try (Scanner lineScanner = new Scanner(console.nextLine())) {
                Map<String, Long> window = new HashMap<>();

                for (int i = 0; i < batchSize && lineScanner.hasNext(); i++) {
                    window.put(lineScanner.next(), number++);
                }
                consumer.accept(window);
            }
        }
    }
}
