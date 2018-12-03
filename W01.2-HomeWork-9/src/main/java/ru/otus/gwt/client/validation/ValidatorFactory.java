/*
 * ValidatorFactory.java
 * This file was last modified at 2018.12.01 16:27 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.gwt.client.validation;

import com.google.gwt.validation.client.AbstractGwtValidatorFactory;
import com.google.gwt.validation.client.GwtValidation;
import com.google.gwt.validation.client.impl.AbstractGwtValidator;
import ru.otus.gwt.shared.User;

import javax.validation.Validator;

import static ru.otus.gwt.client.gin.ApplicationInjector.INSTANCE;

public class ValidatorFactory extends AbstractGwtValidatorFactory
{

    /**
     * Validator marker for the Validation Sample project. Only the classes and groups listed
     * in the {@link GwtValidation} annotation can be validated.
     */
    @GwtValidation(User.class)
    public interface GwtValidator extends Validator
    { /* none */ }

    // @Override
    public AbstractGwtValidator createValidator()
    {
        return (AbstractGwtValidator) INSTANCE.getValidator();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
