package com.barreirasapp.controllers;


import com.barreirasapp.entities.BarrierScenario;
import com.barreirasapp.entities.Report;
import com.barreirasapp.infra.annotation.HttpMethod;
import com.barreirasapp.infra.annotation.Route;
import com.barreirasapp.services.BarrierScenarioService;
import com.barreirasapp.services.LawService;
import com.barreirasapp.services.ReportService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


@WebServlet("/home")
public class HomeController extends Controller {
    private BarrierScenarioService barrierService;
    private ReportService reportService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.barrierService = super.getServiceFactory().getBarrierScenarioService();
        this.reportService = super.getServiceFactory().getReportService();
    }

    @Route(value = "", method = HttpMethod.GET)
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/public/index.jsp");
        List<BarrierScenario> barrierScenarios = barrierService.listAll();
        List<Report> reports = reportService.listAll();

        req.setAttribute("reports", reports);
        req.setAttribute("barrierScenarios", barrierScenarios);

        dispatcher.forward(req, resp);
    }
}