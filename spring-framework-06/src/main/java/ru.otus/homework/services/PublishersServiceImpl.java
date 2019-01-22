package ru.otus.homework.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Publisher;
import ru.otus.homework.services.dao.JdbcPublisherDao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublishersServiceImpl implements PublishersService
{
    public static String[] FIND_ALL_HEADER = {"publisher_id", "publisher_name"};

    private JdbcPublisherDao publisherDao;

    public PublishersServiceImpl(JdbcPublisherDao publisherDao)
    {
        this.publisherDao = publisherDao;
    }

    static String[] unfoldPublisher(Publisher p)
    {
        if (null == p) {
            return new String[]{"NULL", "NULL"};
        }

        return new String[]{Long.toString(p.getId()), p.getPublisherName()};
    }

    private String[] unfold(Publisher a)
    {
        return unfoldPublisher(a);
    }

    @Override
    public List<String[]> findAll()
    {
        List<String[]> head = new ArrayList<>();
        head.add(FIND_ALL_HEADER);

        List<String[]> tail = publisherDao.findAll().stream().map(this::unfold).collect(Collectors.toList());
        head.addAll(tail);

        return head;
    }

    @Override
    public List<String[]> findById(long id)
    {
        List<String[]> head = new ArrayList<>();
        head.add(FIND_ALL_HEADER);

        try {
            Publisher publisher;
            publisher = publisherDao.findById(id);
            head.add(unfold(publisher));

            return head;
        }
        catch (EmptyResultDataAccessException e) {
            return head;

        }
    }

    @Override
    public List<String[]> findByPublisherName(String publisherName)
    {
        List<String[]> head = new ArrayList<>();
        head.add(FIND_ALL_HEADER);

        List<String[]> tail = publisherDao.findByName(publisherName)
            .stream()
            .map(this::unfold)
            .collect(Collectors.toList());
        head.addAll(tail);

        return head;
    }

    @Override
    public long insert(String publisherName)
    {
        Publisher publisher = new Publisher();
        publisher.setPublisherName(publisherName);
        publisherDao.insert(publisher);

        return publisher.getId();
    }

    @Override
    public long update(long id, String publisherName)
    {
        Publisher publisher = new Publisher();
        publisher.setId(id);
        publisher.setPublisherName(publisherName);
        publisherDao.update(publisher);

        return publisher.getId();
    }

    @Override
    public void delete(long id)
    {
        publisherDao.delete(id);
    }
}
