package ru.otus.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.gwt.shared.Emp;
import ru.otus.gwt.shared.Search;

import java.util.ArrayList;
import java.util.List;

import static ru.otus.gwt.shared.Constants.EMPTY;

public class SearchCacheServiceImplTest
{
    private SearchCacheServiceImpl cacheService;

    @Before
    public void setUp()
    {
        cacheService = new SearchCacheServiceImpl();
    }

    @After
    public void tearDown()
    {
        cacheService = null;
    }

    @Test
    public void putToCache()
    {
        Search searchNull = new Search();
        cacheService.putToCache(searchNull, null);
        Assert.assertNull(cacheService.searchInCache(searchNull));

        Search searchEmpty = new Search();
        searchEmpty.setFio(EMPTY);
        searchEmpty.setJob(EMPTY);
        searchEmpty.setCity(EMPTY);
        searchEmpty.setAge(EMPTY);
        List<Emp> expected = new ArrayList<>();
        cacheService.putToCache(searchEmpty, expected);
        List<Emp> test = cacheService.searchInCache(searchEmpty);
        Assert.assertEquals(expected, test);
    }

    @Test
    public void searchInCache()
    {
        Assert.assertNull(cacheService.searchInCache(null));
        Search search = new Search();
        Assert.assertNull(cacheService.searchInCache(search));
    }

    @Test
    public void searchCacheServiceImpl() throws InterruptedException
    {
        SearchCacheServiceImpl searchCacheService = new SearchCacheServiceImpl(10, 10);
        Search search = new Search();
        searchCacheService.putToCache(search, new ArrayList<>());
        Assert.assertNotNull(searchCacheService.searchInCache(search));
        Thread.sleep(50);
        Assert.assertNull(searchCacheService.searchInCache(search));
    }
}