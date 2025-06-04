package com.barreirasapp.controllers;

import com.barreirasapp.dto.report.RegisterReportDTO;
import com.barreirasapp.dto.report.UpdateReportDTO;
import com.barreirasapp.entities.Moderator;
import com.barreirasapp.entities.Report;
import com.barreirasapp.entities.User;
import com.barreirasapp.entities.enums.BarrierType;
import com.barreirasapp.entities.enums.EnvironmentType;
import com.barreirasapp.entities.enums.UserRole;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.infra.annotation.HasRole;
import com.barreirasapp.infra.annotation.HttpMethod;
import com.barreirasapp.infra.annotation.LoginRequired;
import com.barreirasapp.infra.annotation.Route;
import com.barreirasapp.services.BarrierScenarioService;
import com.barreirasapp.services.ReportService;
import com.barreirasapp.utils.ControllerDispatcher;
import com.barreirasapp.utils.routes.RouteInfo;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@WebServlet("/public/report/*")
public class PublicReportController extends Controller {
    private BarrierScenarioService barrierScenarioService;
    private ReportService service;

    @Override
    public void init() throws ServletException {
        super.init();
        this.service = this.getServiceFactory().getReportService();
        this.barrierScenarioService = this.getServiceFactory().getBarrierScenarioService();
    }

    @Route(value = "", method = HttpMethod.GET)
    public void renderPublicList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/public/reportList.jsp");
        req.setAttribute("barrierTypeOptions", BarrierType.values());
        req.setAttribute("scenarios", barrierScenarioService.listAll());

        List<Report> reportList = this.service.listAllValid();

        String[] barrierTypeParams = req.getParameterValues("barrierType");
        String[] lawParams = req.getParameterValues("law");
        String searchTerm = req.getParameter("searchTerm");

        req.setAttribute("reportList", reportList);

        dispatcher.forward(req, resp);
    }

    @Route(value = "{id}/", method = HttpMethod.GET)
    public void renderPublicReport(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/public/report.jsp");

        int reportId = Integer.parseInt(req.getAttribute("id").toString());
        Report report = service.findById(reportId).orElseThrow();

        req.setAttribute("report", report);

        dispatcher.forward(req, resp);
    }
}