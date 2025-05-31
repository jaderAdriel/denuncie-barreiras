package com.barreirasapp.infra.annotation;

import com.barreirasapp.entities.enums.UserRole;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface HasRole {
    public UserRole value ();
}
