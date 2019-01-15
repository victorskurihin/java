package ru.otus.outside.utils;

import ru.otus.homework.models.Author;

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

    public static String DELETE_FROM_AUTHOR_ISBN = "DELETE FROM author_isbn";

    public static String DELETE_FROM_BOOK = "DELETE FROM book";

    public static String DELETE_FROM_AUTHOR = "DELETE FROM author";

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
}
