/*
 * SearchCacheService.java
 * This file was last modified at 2018.12.01 15:52 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.services;

import ru.otus.gwt.shared.Emp;
import ru.otus.gwt.shared.Search;

import java.util.List;

public interface SearchCacheService
{
    void putToCache(Search search, List<Emp> content);

    List<Emp> searchInCache(Search search);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
