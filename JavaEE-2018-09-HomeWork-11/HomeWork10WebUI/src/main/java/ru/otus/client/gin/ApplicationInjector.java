/*
 * ApplicationInjector.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.client.gin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import ru.otus.client.service.LoginServiceAsync;
import ru.otus.client.service.InsideServiceAsync;
import ru.otus.client.text.ApplicationConstants;
import ru.otus.client.validation.ValidatorFactory.GwtValidator;
import ru.otus.client.widget.AddView.AddViewUiBinder;
import ru.otus.client.widget.LoginView.LoginViewUiBinder;
import ru.otus.client.widget.PayView.PayViewUiBinder;
import ru.otus.client.widget.SearchView.SearchViewUiBinder;
import ru.otus.client.widget.image.ApplicationImages;

@GinModules(ApplicationGinModule.class)
public interface ApplicationInjector extends Ginjector
{

    ApplicationInjector INSTANCE = GWT.create(ApplicationInjector.class);

    AddViewUiBinder getAddViewUiBinder();

    ApplicationConstants getConstants();

    ApplicationImages getImages();

    GwtValidator getValidator();

    InsideServiceAsync getInsideService();

    LoginServiceAsync getLoginService();

    LoginViewUiBinder getLoginViewUiBinder();

    PayViewUiBinder getPayViewUiBinder();

    SearchViewUiBinder getSearchViewUiBinder();
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
