/*
 * ValidationRule.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.gwt.shared.validation;

import ru.otus.gwt.shared.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidationRule
{
    public static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static boolean isValid(User user)
    {
        return getErrors(user).isEmpty();
    }

    public static Set<ConstraintViolation<User>> getErrors(User user)
    {
        return validator.validate(user);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
