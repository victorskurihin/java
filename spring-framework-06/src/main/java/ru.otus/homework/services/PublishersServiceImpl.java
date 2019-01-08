package ru.otus.homework.services;

import org.springframework.shell.table.TableBuilder;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Publisher;
import ru.otus.homework.services.dao.JdbcPublisherDao;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublishersServiceImpl extends TableBuilderService<Publisher, JdbcPublisherDao> implements PublishersService
{
    private JdbcPublisherDao publisherDao;

    private TableBuilder tableBuilder;

    public PublishersServiceImpl(JdbcPublisherDao dao)
    {
        this.publisherDao = dao;
    }

    @Override
    public TableBuilder getTableBuilder()
    {
        return tableBuilder;
    }

    private static String[] unfoldPublisher(Publisher publisher)
    {
        return new String[]{Long.toString(publisher.getId()), publisher.getPublisherName()};
    }

    @Override
    public int createTableForFindAll()
    {
        tableBuilder = super.createTableForFindAll(
            publisherDao, JdbcPublisherDao.FIND_ALL_HEADER, PublishersServiceImpl::unfoldPublisher
        );

        return tableBuilder.getModel().getRowCount() - 1;
    }

    private static String[] unfoldWithDetail(Publisher publisher, Book book, Author author)
    {
        return new String[]{
            Long.toString(book.getId()),
            book.getIsbn(),
            book.getTitle(),
            Integer.toString(book.getEditionNumber()),
            publisher.getPublisherName(),
            book.getCopyright(),
            author.getFirstName(),
            author.getLastName()
        };
    }

    @Override
    public int createTableAllBookWithDetail()
    {
        List<Publisher> publishers = publisherDao.findAllWithDetail();
        List<String[]> dataList = new ArrayList<>();

        dataList.add(JdbcPublisherDao.FIND_ALL_WITH_DETAIL_HEADER);

        for (Publisher publisher : publishers) {
            for (Book book : publisher.getBooks() ) {
                for (Author author : book.getAuthors()) {
                    dataList.add(unfoldWithDetail(publisher, book, author));
                }
            }
        }

        String[][] data = new String[dataList.size()][];
        data = dataList.toArray(data);
        tableBuilder = createTableBuilder(data);

        return tableBuilder.getModel().getRowCount() - 1;
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
