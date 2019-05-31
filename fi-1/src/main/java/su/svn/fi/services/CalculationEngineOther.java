package su.svn.fi.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import su.svn.fi.models.Instrument;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class CalculationEngineOther implements CalculationEngine, Runnable
{
    private static final Log LOG = LogFactory.getLog(CalculationEngineOther.class);

    private final String name;

    private final Comparator<Instrument> comparator = Comparator.comparingLong(Instrument::getNumber);
     // TODO read from property initialCapacity
    private final PriorityBlockingQueue<Instrument> queue = new PriorityBlockingQueue<>(100, comparator);
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public CalculationEngineOther(String name)
    {
        this.name = name;
    }

    @PostConstruct
    void init()
    {
        LOG.debug("init");
        executorService.submit(this);
    }

    @Override
    public void apply(Instrument instrument)
    {
        LOG.debug("apply: " + instrument);
        queue.put(instrument);
    }

    @Override
    public double getResult()
    {
        Comparator<Instrument> reverse = comparator.reversed();
        PriorityBlockingQueue<Instrument> reverseOrder = new PriorityBlockingQueue<>(100, reverse);
        queue.drainTo(reverseOrder);
        List<Instrument> lastTen = new ArrayList<>(10);
        reverseOrder.drainTo(lastTen, 10);
        return lastTen.stream().map(Instrument::getValue).reduce(0.0, (a, b) -> a + b);
    }

    @Override
    public void run()
    {
        LOG.debug("run started");
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                LOG.debug("run sleep");
                // TODO read from property sleep time
                Thread.sleep(100);
                synchronized (this) {
                    LOG.debug("run synchronized");
                    if (queue.size() > 10) {
                        System.out.println("queue.size() = " + queue.size());
                        queue.poll();
                        System.out.println("queue = " + queue);
                    }
                }
            } catch (InterruptedException e) {
                LOG.error(e);
            }
        }
    }

    @PreDestroy
    void shutdown()
    {
        LOG.debug("shutdown");
        executorService.shutdown();
    }
}
