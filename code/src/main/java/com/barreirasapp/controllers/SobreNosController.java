package com.barreirasapp.controllers;


import com.barreirasapp.entities.BarrierScenario;
import com.barreirasapp.entities.Report;
import com.barreirasapp.infra.annotation.HttpMethod;
import com.barreirasapp.infra.annotation.Route;
import com.barreirasapp.services.BarrierScenarioService;
import com.barreirasapp.services.ReportService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


@WebServlet("/sobre/")
public class SobreNosController extends Controller {
    @Route(value = "", method = HttpMethod.GET)
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/public/sobre-nos.jsp");
        dispatcher.forward(req, resp);
    }
}