package su.svn.fi.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

@Service
public class ReaderStdIn implements Reader
{
    private final int batchSize = 2; // TODO read from property

    private Scanner console;

    public ReaderStdIn(Scanner scanner)
    {
        console = scanner;
    }

    public ReaderStdIn()
    {
        this(new Scanner(System.in));
    }

    @Override
    public void read(Consumer<List<String>> consumer)
    {
        while (console.hasNextLine()) {
            try (Scanner lineScanner = new Scanner(console.nextLine())) {
                List<String> list = new ArrayList<>();

                for (int i = 0; i < batchSize && lineScanner.hasNext(); i++) {
                    list.add(lineScanner.next());
                }
                consumer.accept(list);
            }
        }
    }
}
