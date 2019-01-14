package ru.otus.outside.utils;

import ru.otus.homework.models.Author;

public class TestData
{
    public static String DELETE_FROM_AUTHOR_ISBN = "DELETE FROM author_isbn";

    public static String DELETE_FROM_BOOK = "DELETE FROM book";

    public static String DELETE_FROM_AUTHOR = "DELETE FROM author";

    // Author(id=6, firstName=test_first_name_6, lastName=test_last_name_6)
    public static Author createAuthor6()
    {
        Author result = new Author();
        result.setId(6L);
        result.setFirstName("test_first_name_6");
        result.setLastName("test_last_name_6");

        return result;
    }

    // Author(id=7, firstName=test_first_name_7, lastName=test_last_name_7),
    public static Author createAuthor7()
    {
        Author result = new Author();
        result.setId(7L);
        result.setFirstName("test_first_name_7");
        result.setLastName("test_last_name_7");

        return result;
    }

    // Author(id=8, firstName=test_first_name_8, lastName=test_last_name_8)]
    public static Author createAuthor8()
    {
        Author result = new Author();
        result.setId(8L);
        result.setFirstName("test_first_name_8");
        result.setLastName("test_last_name_8");

        return result;
    }

    // Author(id=9, firstName=test_first_name_9, lastName=test_last_name_9)]
    public static Author createAuthor9()
    {
        Author result = new Author();
        result.setId(9L);
        result.setFirstName("test_first_name_9");
        result.setLastName("test_last_name_9");

        return result;
    }
}
