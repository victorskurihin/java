/*
 * SearchCacheServiceImpl.java
 * This file was last modified at 2018.12.01 15:52 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.otus.cache.CacheEngine;
import ru.otus.cache.CacheEngineImpl;
import ru.otus.cache.MyElement;
import ru.otus.gwt.shared.Emp;
import ru.otus.gwt.shared.Search;

import java.util.List;


public class SearchCacheServiceImpl implements SearchCacheService
{
    public static final int cacheSize = 10;
    private static final Logger LOGGER = LogManager.getLogger(SearchCacheServiceImpl.class.getName());

    private CacheEngine<Integer, List<Emp>> cache;

    public SearchCacheServiceImpl()
    {
        cache = new CacheEngineImpl<>(cacheSize, 0, 0, true);
    }

    public SearchCacheServiceImpl(long lifeTimeMs, long idleTimeMs)
    {
        cache = new CacheEngineImpl<>(cacheSize, lifeTimeMs, idleTimeMs, false);
    }

    @Override
    public void putToCache(Search search, List<Emp> content)
    {
        Integer hash = search.hashCode();
        LOGGER.info("Put to cache by hash: {}", hash);
        cache.put(new MyElement<>(hash, content));
    }

    /**
     * The method return element from cache.
     * If element hasn't in cache method return null.
     *
     * @param search search request.
     * @return - element from cache or null.
     */
    @Override
    public List<Emp> searchInCache(Search search)
    {
        if (null == search) return null;
        Integer hash = search.hashCode();
        LOGGER.info("Searching by hash: {} ...", hash);
        MyElement<Integer, List<Emp>> element = cache.get(hash);
        if (null == element) return null;
        LOGGER.info("Get from cache by hash: {}", hash);
        return element.getValue();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
