package ru.otus.homework.services;

import org.springframework.shell.table.*;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.services.dao.JdbcBookDao;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@Service
public class BooksServiceImpl extends TableBuilderService<Book, JdbcBookDao> implements BooksService
{
    private JdbcBookDao bookDao;

    private TableBuilder tableBuilder;

    public BooksServiceImpl(JdbcBookDao dao)
    {
        this.bookDao = dao;
    }

    @Override
    public TableBuilder getTableBuilder()
    {
        return tableBuilder;
    }

    private static String[] unfoldBook(Book book)
    {
        return new String[]{
            Long.toString(book.getId()),
            book.getIsbn(),
            book.getTitle(),
            Integer.toString(book.getEditionNumber()),
            book.getCopyright(),
            Long.toString(book.getPublisherId())
        };
    }

    @Override
    public int createTableForFindAll()
    {
        tableBuilder = super.createTableForFindAll(bookDao, JdbcBookDao.FIND_ALL_HEADER, BooksServiceImpl::unfoldBook);

        return tableBuilder.getModel().getRowCount() - 1;
    }

    private static String[] unfoldBookAndAutherWithDetail(Book book, Author author)
    {
        return new String[]{
            Long.toString(book.getId()),
            book.getIsbn(),
            book.getTitle(),
            Integer.toString(book.getEditionNumber()),
            book.getCopyright(),
            author.getFirstName(),
            author.getLastName()
        };
    }

    private int createTableAllBookWithDetail(BiFunction<Book, Author, String[]> f)
    {
        List<Book> bookList = bookDao.findAllWithDetail();
        List<String[]> dataList = new ArrayList<>();

        dataList.add(JdbcBookDao.FIND_ALL_WITH_DETAIL_HEADER);

        for (Book book : bookList) {
            for (Author author : book.getAuthors()) {
                dataList.add(unfoldBookAndAutherWithDetail(book, author));
            }
        }

        String[][] data = new String[dataList.size()][];
        data = dataList.toArray(data);
        tableBuilder = createTableBuilder(data);

        return tableBuilder.getModel().getRowCount() - 1;
    }

    @Override
    public int createTableForFindAllWithDetail()
    {
        return createTableAllBookWithDetail(BooksServiceImpl::unfoldBookAndAutherWithDetail);
    }

    @Override
    public long insert(String isbn, String title, int editionNumber, String copyright, long publisherId)
    {
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setEditionNumber(editionNumber);
        book.setCopyright(copyright);
        book.setPublisherId(publisherId);

        bookDao.insert(book);

        return book.getId();
    }

    @Override
    public long update(long id, String isbn, String title, int editionNumber, String copyright, long publisherId)
    {
        Book book = new Book();
        book.setId(id);
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setEditionNumber(editionNumber);
        book.setCopyright(copyright);
        book.setPublisherId(publisherId);

        bookDao.update(book);

        return book.getId();
    }

    @Override
    public void delete(long id)
    {
        bookDao.delete(id);
    }
}
