package ru.otus.gwt.shared;

/*
 * Created by VSkurikhin at autumn 2018.
 */

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
