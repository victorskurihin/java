/*
 * Search.java
 * This file was last modified at 2018.12.01 16:35 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.gwt.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Search implements IsSerializable
{
    private String fio;
    private String job;
    private String city;
    private String age;

    public Search()
    { /* None */ }

    public Search(String fio, String job, String city, String age)
    {
        String trimmed = fio.trim();
        if (trimmed.length() > 0) {
            this.fio = trimmed;
        }
        trimmed = job.trim();
        if (trimmed.length() > 0) {
            this.job = job;
        }
        trimmed = city.trim();
        if (trimmed.length() > 0) {
            this.city = city;
        }
        trimmed = age.trim();
        if (trimmed.length() > 0) {
            this.age = age;
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
