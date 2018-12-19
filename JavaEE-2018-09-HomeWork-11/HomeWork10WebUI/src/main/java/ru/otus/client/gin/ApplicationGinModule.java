/*
 * ApplicationGinModule.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import ru.otus.client.service.LoginService;
import ru.otus.client.text.ApplicationConstants;
import ru.otus.client.validation.ValidatorFactory.GwtValidator;
import ru.otus.client.widget.AddView.AddViewUiBinder;
import ru.otus.client.widget.LoginView.LoginViewUiBinder;
import ru.otus.client.widget.PayView.PayViewUiBinder;
import ru.otus.client.widget.SearchView.SearchViewUiBinder;
import ru.otus.client.widget.image.ApplicationImages;

public class ApplicationGinModule extends AbstractGinModule
{
    protected void configure()
    {
        bind(AddViewUiBinder.class);
        bind(ApplicationConstants.class);
        bind(ApplicationImages.class);
        bind(GwtValidator.class);
        bind(LoginService.class);
        bind(LoginViewUiBinder.class);
        bind(PayViewUiBinder.class);
        bind(SearchViewUiBinder.class);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
