package ru.otus.gwt.shared;

/*
 * Created by VSkurikhin at autumn 2018.
 */

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

    public Search() { /* None */ }

    public Search(String fio, String job, String city, String age) {
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
