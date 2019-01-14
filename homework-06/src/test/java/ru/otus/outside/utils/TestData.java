package ru.otus.outside.utils;

import ru.otus.homework.models.Author;

public class TestData
{
    public static Author createAuthor6()
    {
        Author result = new Author();
        result.setId(6L);
        result.setFirstName("test_first_name_6");
        result.setLastName("test_last_name_6");

        return result;
    }
}
