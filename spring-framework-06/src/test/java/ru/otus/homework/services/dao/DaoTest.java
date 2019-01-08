package ru.otus.homework.services.dao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DaoTest
{
    @Test
    void testHeaderSize()
    {
        assertEquals(0, Dao.FIND_ALL_HEADER.length);
    }
}