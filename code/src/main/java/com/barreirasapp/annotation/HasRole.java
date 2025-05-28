package com.barreirasapp.annotation;

import com.barreirasapp.model.enums.UserRole;

public @interface HasRole {
    public UserRole role ();
}
