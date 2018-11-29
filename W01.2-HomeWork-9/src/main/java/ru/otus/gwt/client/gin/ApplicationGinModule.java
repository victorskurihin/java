/*
 * ApplicationGinModule.java
 * This file was last modified at 29.11.18 10:48 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.gwt.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import ru.otus.gwt.client.service.LoginService;
import ru.otus.gwt.client.text.ApplicationConstants;
import ru.otus.gwt.client.validation.ValidatorFactory.GwtValidator;
import ru.otus.gwt.client.widget.AddView.AddViewUiBinder;
import ru.otus.gwt.client.widget.LoginView.LoginViewUiBinder;
import ru.otus.gwt.client.widget.SearchView.SearchViewUiBinder;
import ru.otus.gwt.client.widget.TaxView.TaxViewUiBinder;
import ru.otus.gwt.client.widget.image.ApplicationImages;

public class ApplicationGinModule extends AbstractGinModule {
    protected void configure() {
        bind(AddViewUiBinder.class);
        bind(ApplicationConstants.class);
        bind(ApplicationImages.class);
        bind(GwtValidator.class);
        bind(LoginService.class);
        bind(LoginViewUiBinder.class);
        bind(SearchViewUiBinder.class);
        bind(TaxViewUiBinder.class);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
