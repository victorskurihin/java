/*
 * Emp.java
 * This file was last modified at 29.11.18 11:01 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.gwt.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import ru.otus.models.EmpEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants(prefix = "")
public class Emp implements IsSerializable
{
    @NotNull
    private long id;

    @NotNull
    @Size(min = 2, message = "First name must contain at least 2 characters.")
    private String firstName;

    @NotNull
    @Size(min = 2, message = "Second name must contain at least 2 characters.")
    private String secondName;

    @NotNull
    @Size(min = 2, message = "Surname must contain at least 2 characters.")
    private String surName;

    @NotNull
    @Size(min = 2, message = "Job must contain at least 2 characters.")
    private String job;

    @NotNull
    @Size(min = 2, message = "City must contain at least 2 characters.")
    private String city;

    @NotNull
    @Size(min = 2, message = "Age must contain at least 2 characters.")
    private String age;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
