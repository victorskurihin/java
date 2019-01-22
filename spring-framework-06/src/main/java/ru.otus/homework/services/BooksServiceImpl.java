package ru.otus.homework.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.Publisher;
import ru.otus.homework.services.dao.JdbcBookDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BooksServiceImpl implements BooksService
{
    public static String[] FIND_ALL_HEADER = {
        "book_id", "isbn", "title", "edition_number", "copyright", "publisher_id", "genre_id"
    };

    public static String[] FIND_ALL_HEADER_BOOKS_AUTHORS = {
        "book_id", "isbn", "title", "edition_number", "copyright", "publisher_name", "genre", "first_name", "last_name"
    };

    private static final Logger LOGGER = LoggerFactory.getLogger(BooksServiceImpl.class);

    private JdbcBookDao bookDao;

    public BooksServiceImpl(JdbcBookDao bookDao)
    {
        this.bookDao = bookDao;
    }

    static String[] unfoldBook(Book b)
    {
        if (null == b) {
            return new String[]{"NULL", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL"};
        }

        return new String[]{
            Long.toString(b.getId()),
            b.getIsbn(),
            b.getTitle() == null ? "NULL" : b.getTitle(),
            Integer.toString(b.getEditionNumber()),
            b.getCopyright() == null ? "NULL" : b.getCopyright(),
            b.getPublisher() == null ? "NULL" : Long.toString(b.getPublisher().getId()),
            b.getGenre() == null ? "NULL" : Long.toString(b.getGenre().getId()),
        };
    }

    private String[] mergeBookAndAuthor(Book b, Author a)
    {
        if (null == b) {
            LOGGER.error("Book is null.");
            throw new RuntimeException();
        }

        if (null == a) {
            return new String[]{
                Long.toString(b.getId()),
                b.getIsbn(),
                b.getTitle() == null ? "NULL" : b.getTitle(),
                Integer.toString(b.getEditionNumber()),
                b.getCopyright() == null ? "NULL" : b.getCopyright(),
                b.getPublisher() == null ? "NULL" : b.getPublisher().getPublisherName(),
                b.getGenre() == null ? "NULL" : b.getGenre().getGenre(),
                "NULL", "NULL"
            };
        }

        return new String[]{
            Long.toString(b.getId()),
            b.getIsbn(),
            b.getTitle() == null ? "NULL" : b.getTitle(),
            Integer.toString(b.getEditionNumber()),
            b.getCopyright() == null ? "NULL" : b.getCopyright(),
            b.getPublisher() == null ? "NULL" : b.getPublisher().getPublisherName(),
            b.getGenre() == null ? "NULL" : b.getGenre().getGenre(),
            a.getFirstName() == null ? "NULL" : a.getFirstName(),
            a.getLastName() == null ? "NULL" : a.getLastName(),
        };
    }

    private String[] unfold(Book a)
    {
        return unfoldBook(a);
    }

    @Override
    public List<String[]> findAll()
    {
        List<String[]> head = new ArrayList<>();
        head.add(FIND_ALL_HEADER);

        List<String[]> tail = bookDao.findAll().stream().map(this::unfold).collect(Collectors.toList());
        head.addAll(tail);

        return head;
    }

    @Override
    public List<String[]> findById(long id)
    {
        List<String[]> head = new ArrayList<>();
        head.add(FIND_ALL_HEADER);

        try {
            Book book;
            book = bookDao.findById(id);
            head.add(unfold(book));

            return head;
        }
        catch (EmptyResultDataAccessException e) {
            return head;
        }
    }

    @Override
    public List<String[]> findByIsbn(String isbn)
    {
        List<String[]> head = new ArrayList<>();
        head.add(FIND_ALL_HEADER);

        String[] tail = unfold(bookDao.findByIsbn(isbn));
        head.add(tail);

        return head;
    }

    @Override
    public List<String[]> findByTitle(String title)
    {
        List<String[]> head = new ArrayList<>();
        head.add(FIND_ALL_HEADER);

        List<String[]> tail = bookDao.findByTitle(title)
            .stream()
            .map(this::unfold)
            .collect(Collectors.toList());
        head.addAll(tail);

        return head;
    }

    @Override
    public List<String[]> findAllBooksAndTheirAuthors()
    {
        List<String[]> result = new ArrayList<>();
        result.add(FIND_ALL_HEADER_BOOKS_AUTHORS);
        List<Book> books = bookDao.findAllBooksAndTheirAuthors();

        for (Book book : books) {
            if (book.getAuthors().isEmpty()) {
                result.add(mergeBookAndAuthor(book, null));
            }
            else {
                for (Author author : book.getAuthors()) {
                    result.add(mergeBookAndAuthor(book, author));
                }
            }
        }

        return result;
    }

    @Override
    public long insert(String isbn, String title, int edition_number, String copyright, long publisherId, long genreId)
    {
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setEditionNumber(edition_number);
        book.setCopyright(copyright);
        Publisher publisher = new Publisher();
        publisher.setId(publisherId);
        book.setPublisher(publisher);
        Genre genre = new Genre();
        genre.setId(genreId);
        book.setGenre(genre);
        bookDao.insert(book);

        return book.getId();
    }

    @Override
    public long update(long id, String isbn, String title, int editionNum, String copyright, long pblshrId, long genreId)
    {
        Book book = new Book();
        book.setId(id);
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setEditionNumber(editionNum);
        book.setCopyright(copyright);
        Publisher publisher = new Publisher();
        publisher.setId(pblshrId);
        book.setPublisher(publisher);
        Genre genre = new Genre();
        genre.setId(genreId);
        book.setGenre(genre);
        bookDao.update(book);

        return book.getId();
    }

    @Override
    public void delete(long id)
    {
        bookDao.delete(id);
    }
}
