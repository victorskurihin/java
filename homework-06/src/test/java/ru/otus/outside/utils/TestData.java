package ru.otus.outside.utils;

import ru.otus.homework.models.*;
import org.springframework.boot.jdbc.DataSourceInitializationMode;

public class TestData
{
    public static String TEST = "test";

    public static long TEST_ID = 1L;

    public static int TEST_NUM = 3;

    public static String TEST_FIRST_NAME = "test_first_name_1";

    public static String TEST_LAST_NAME = "test_last_name_1";

    public static String TEST_ISBN = "test_isbn_name_1";

    public static String TEST_TITLE = "test_title_name_1";

    public static String TEST_COPYRIGHT= "test_copyright_1";

    public static String TEST_GENRE_NAME = "test_genre_1";

    public static String TEST_PUBLISHER_NAME = "test_publisher_1";

    public static String TEST_COMMENT_NAME = "test_comment_1";

    public static String DELETE_FROM_AUTHOR_ISBN = "DELETE FROM author_isbn";

    public static String DELETE_FROM_BOOK = "DELETE FROM book";

    public static String DELETE_FROM_AUTHOR = "DELETE FROM author";

    public static String DELETE_FROM_GENRE = "DELETE FROM genre";

    public static String DELETE_FROM_PUBLISHER = "DELETE FROM publisher";

    public static String DELETE_FROM_COMMENT = "DELETE FROM comment";

    public static Publisher createPublisher1()
    {
        Publisher result = new Publisher();
        result.setId(1L);
        result.setPublisherName("test_publisher_name_1");

        return result;
    }

    public static Genre createGenre2()
    {
        Genre result = new Genre();
        result.setId(2L);
        result.setGenre("test_genre_2");

        return result;
    }

    public static Book createBook3()
    {
        Book result = new Book();
        result.setId(3L);
        result.setIsbn("9999999993");
        result.setTitle("test_title_3");
        result.setEditionNumber(3);
        result.setCopyright("2003");
        result.setPublisher(createPublisher1());
        result.setGenre(createGenre2());
        Comment comment31 = createComment31();
        comment31.setBook(result);
        result.getComments().add(comment31);
        Comment comment32 = createComment31();
        comment32.setBook(result);
        result.getComments().add(comment32);

        return result;
    }

    public static Book createBook4()
    {
        Book result = new Book();
        result.setId(4L);
        result.setIsbn("9999999994");
        result.setTitle("test_title_4");
        result.setEditionNumber(4);
        result.setCopyright("2004");
        result.setPublisher(createPublisher1());
        result.setGenre(createGenre2());
        result.getComments().add(createComment41());
        Comment comment41 = createComment41();
        comment41.setBook(result);
        result.getComments().add(comment41);

        return result;
    }

    public static Book createBook5()
    {
        Book result = new Book();
        result.setId(5L);
        result.setIsbn("9999999995");
        result.setTitle("test_title_5");
        result.setEditionNumber(5);
        result.setCopyright("2005");
        result.setPublisher(createPublisher1());
        result.setGenre(createGenre2());

        return result;
    }

    public static Author createAuthor6()
    {
        Author result = new Author();
        result.setId(6L);
        result.setFirstName("test_first_name_6");
        result.setLastName("test_last_name_6");

        return result;
    }

    public static Author createAuthor7()
    {
        Author result = new Author();
        result.setId(7L);
        result.setFirstName("test_first_name_7");
        result.setLastName("test_last_name_7");

        return result;
    }

    public static Author createAuthor8()
    {
        Author result = new Author();
        result.setId(8L);
        result.setFirstName("test_first_name_8");
        result.setLastName("test_last_name_8");

        return result;
    }

    public static Author createAuthor9()
    {
        Author result = new Author();
        result.setId(9L);
        result.setFirstName("test_first_name_9");
        result.setLastName("test_last_name_9");

        return result;
    }

    public static Comment createComment32()
    {
        Comment result = new Comment();
        result.setId(32L);
        result.setComment("test_comment_32");
        result.setBook(null);

        return result;
    }

    public static Comment createComment41()
    {
        Comment result = new Comment();
        result.setId(41L);
        result.setComment("test_comment_41");
        result.setBook(null);

        return result;
    }

    public static Comment createComment31()
    {
        Comment result = new Comment();
        result.setId(31L);
        result.setComment("test_comment_31");
        result.setBook(null);

        return result;
    }
}
