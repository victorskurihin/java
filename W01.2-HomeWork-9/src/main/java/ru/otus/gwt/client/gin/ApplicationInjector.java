/*
 * ApplicationInjector.java
 * This file was last modified at 29.11.18 10:48 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.gwt.client.gin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import ru.otus.gwt.client.service.LoginServiceAsync;
import ru.otus.gwt.client.service.InsideServiceAsync;
import ru.otus.gwt.client.text.ApplicationConstants;
import ru.otus.gwt.client.validation.ValidatorFactory.GwtValidator;
import ru.otus.gwt.client.widget.AddView.AddViewUiBinder;
import ru.otus.gwt.client.widget.SearchView.SearchViewUiBinder;
import ru.otus.gwt.client.widget.TaxView.TaxViewUiBinder;
import ru.otus.gwt.client.widget.LoginView.LoginViewUiBinder;
import ru.otus.gwt.client.widget.image.ApplicationImages;

@GinModules(ApplicationGinModule.class)
public interface ApplicationInjector extends Ginjector {

    ApplicationInjector INSTANCE = GWT.create(ApplicationInjector.class);

    AddViewUiBinder getAddViewUiBinder();
    ApplicationConstants getConstants();
    ApplicationImages getImages();
    GwtValidator getValidator();
    InsideServiceAsync getInsideService();
    LoginServiceAsync getLoginService();
    LoginViewUiBinder getLoginViewUiBinder();
    SearchViewUiBinder getSearchViewUiBinder();
    TaxViewUiBinder getTaxViewUiBinder();
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
