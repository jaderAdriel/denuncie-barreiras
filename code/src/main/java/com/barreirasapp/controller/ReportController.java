package com.barreirasapp.controller;

import com.barreirasapp.annotation.HttpMethod;
import com.barreirasapp.annotation.LoginRequired;
import com.barreirasapp.annotation.Route;
import com.barreirasapp.dto.law.RegisterLawDTO;
import com.barreirasapp.dto.law.UpdateLawDTO;
import com.barreirasapp.dto.report.RegisterReportDTO;
import com.barreirasapp.dto.report.UpdateReportDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.infra.security.UserContext;
import com.barreirasapp.model.entities.Law;
import com.barreirasapp.model.entities.Report;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.service.LawService;
import com.barreirasapp.service.ReportService;
import com.barreirasapp.utils.ControllerDispatcher;
import com.barreirasapp.utils.routes.RouteInfo;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@WebServlet("/report/*")
public class ReportController extends HttpServlet {
    private Map<String, RouteInfo> routes;

    private ReportService service;

    @Override
    public void init() throws ServletException {
        this.service = new ReportService();
        this.routes = Middleware.setupRoutes(this.getClass());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Middleware.callRoute(this, routes, req, resp);
    }

    @Route(value = "index/", method = HttpMethod.GET)
    public void renderList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/report/index.jsp");

        List<Report> reportList = this.service.listAll();

        req.setAttribute("reportList", reportList);

        dispatcher.forward(req, resp);
    }

    @LoginRequired
    @Route(value = "create/", method = HttpMethod.GET_POST)
    public void createReport(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("action", "/report/create/");
        req.setAttribute("method", "POST");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/report/form.jsp");

        if (req.getMethod().equalsIgnoreCase("GET")) {
            dispatcher.forward(req, resp);
            return;
        }

        String environment = req.getParameter("environment");
        String incidentDetails = req.getParameter("incidentDetails");
        String barrierScenario = req.getParameter("barrierScenario");
        String anonymous = req.getParameter("anonymous");



        req.setAttribute("environment", environment);
        req.setAttribute("incidentDetails", incidentDetails);
        req.setAttribute("barrierScenario", barrierScenario);
        req.setAttribute("anonymous", anonymous);

        try {
            RegisterReportDTO reportDTO = new RegisterReportDTO(environment, incidentDetails, barrierScenario, anonymous);

            if (!reportDTO.getAnonymous()) {
                String sessionId = UserContext.getSessionId(req.getCookies());
                Optional<User> reporter = UserContext.getUserFromSession(sessionId);

                if (reporter.isEmpty()) {
                    resp.sendRedirect("/accounts/login/");
                    return;
                }

                reportDTO.setReporter(reporter.get());
            }

            this.service.insert(reportDTO);
            resp.sendRedirect("/report/index/");
        } catch (ValidationError e) {
            ControllerDispatcher.sendErrors(e.getErrors(), req);
            dispatcher.forward(req, resp);
        }
    }

    @LoginRequired
    @Route(value = "update/{id}", method = HttpMethod.GET_POST)
    public void updateReport(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String reportId = req.getAttribute("id").toString();
        req.setAttribute("action", "/report/update/" + reportId);
        req.setAttribute("method", "PUT");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/report/form.jsp");
        if (req.getMethod().equalsIgnoreCase("GET")) {
            setReportFields(req, reportId);
            dispatcher.forward(req, resp);
            return;
        }

        String newEnvironment = req.getParameter("environment");
        String newIncidentDetails = req.getParameter("incidentDetails");
        String newRelatedScenario = req.getParameter("relatedScenario");

        System.out.println("newIncidentDetails=" + newIncidentDetails);
        System.out.println("newRelatedScenario=" + newRelatedScenario);
        System.out.println("id=" + reportId);

        try {
            UpdateReportDTO updateReportDTO = new UpdateReportDTO(reportId, newIncidentDetails, newRelatedScenario);
            this.service.update(updateReportDTO);
            resp.sendRedirect("/report/index/");
            System.out.println("Tecnicamente deu tudo certo");
        } catch (ValidationError e) {
            System.out.println(e.getMessage());
            setReportFields(req, reportId);
            ControllerDispatcher.sendErrors(e.getErrors(), req);
            dispatcher.forward(req, resp);
        }

        req.setAttribute("environment", newEnvironment);
        req.setAttribute("incidentDetails", newIncidentDetails);
    }

    @LoginRequired
    @Route(value = "delete/{Id}", method = HttpMethod.GET_POST)
    public void deleteReport(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String reportId = req.getAttribute("Id").toString();

        req.setAttribute("method", "DELETE");
        req.setAttribute("action", "/report/delete/" + reportId);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/report/form.jsp");
        if (req.getMethod().equalsIgnoreCase("GET")) {
            setReportFields(req, reportId);
            dispatcher.forward(req, resp);
            return;
        }

        try {
            this.service.deleteById(Integer.valueOf(reportId));
        } catch (ValidationError e) {
            resp.sendRedirect("/report/index/");
            dispatcher.forward(req, resp);
        } finally {
            resp.sendRedirect("/report/index/");
            dispatcher.forward(req, resp);
        }
    }


    public void setReportFields(HttpServletRequest req, String reportId) {
        Report report = service.findById(Integer.valueOf(reportId));

        req.setAttribute("id", report.getAmbient());
        req.setAttribute("incidentDetails", report.getEventDetailing());
        req.setAttribute("relatedScenarioId", report.getBarrierScenario());
        req.setAttribute("anonymous", report.getAnonymousReport());
        req.setAttribute("reporter", report.getReporter());
    }

}