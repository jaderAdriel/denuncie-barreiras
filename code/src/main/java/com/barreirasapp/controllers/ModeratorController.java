package com.barreirasapp.controllers;

import com.barreirasapp.services.AuthService;
import jakarta.servlet.ServletException;

public class ModeratorController extends Controller {
    private AuthService service;

    @Override
    public void init() throws ServletException {
        super.init();
        service = getServiceFactory().getAuthService();
    }
}
