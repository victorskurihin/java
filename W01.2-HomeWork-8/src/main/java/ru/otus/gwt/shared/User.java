package ru.otus.gwt.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants(prefix = "")
public class User implements IsSerializable
{

    @NotNull
    @Size(min = 4, message = "Inside must contain at least 4 characters.")
    private String login;

    @NotNull
    @Size(min = 4, message = "Password must contain at least 4 characters.")
    private String password;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
