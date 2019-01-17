package ru.otus.homework.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Publisher;
import ru.otus.homework.repository.PublisherRepositoryJpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublishersServiceImpl implements PublishersService
{
    private PublisherRepositoryJpa publisherRepository;

    public PublishersServiceImpl(PublisherRepositoryJpa publisherRepository)
    {
        this.publisherRepository = publisherRepository;
    }

    static String[] unfoldPublisher(Publisher p)
    {
        if (null == p) {
            return new String[]{"NULL", "NULL"};
        }

        return new String[]{Long.toString(p.getId()), p.getPublisherName()};
    }

    private String[] unfold(Publisher g)
    {
        return unfoldPublisher(g);
    }

    @Override
    public List<String[]> findAll()
    {
        List<String[]> head = new ArrayList<>();
        head.add(PublisherRepositoryJpa.FIND_ALL_HEADER);

        List<String[]> tail = publisherRepository.findAll().stream().map(this::unfold).collect(Collectors.toList());
        head.addAll(tail);

        return head;
    }

    @Override
    public List<String[]> findById(long id)
    {
        List<String[]> head = new ArrayList<>();
        head.add(PublisherRepositoryJpa.FIND_ALL_HEADER);

        try {
            Publisher publisher;
            publisher = publisherRepository.findById(id);
            head.add(unfold(publisher));

            return head;
        }
        catch (EmptyResultDataAccessException e) {
            return head;

        }
    }

    @Override
    public List<String[]> findByPublisher(String publisher)
    {
        List<String[]> head = new ArrayList<>();
        head.add(PublisherRepositoryJpa.FIND_ALL_HEADER);

        List<String[]> tail = publisherRepository.findByPublisher(publisher)
            .stream()
            .map(this::unfold)
            .collect(Collectors.toList());
        head.addAll(tail);

        return head;
    }

    @Override
    public long insert(String value)
    {
        Publisher publisher = new Publisher();
        publisher.setPublisherName(value);
        publisherRepository.save(publisher);

        return publisher.getId();
    }

    @Override
    public long update(long id, String value)
    {
        Publisher publisher = publisherRepository.findById(id);
        publisher.setPublisherName(value);
        publisherRepository.save(publisher);

        return publisher.getId();
    }

    @Override
    public void delete(long id)
    {
        publisherRepository.delete(id);
    }
}
