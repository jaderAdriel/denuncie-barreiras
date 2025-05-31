package com.barreirasapp.controllers;

import com.barreirasapp.entities.Moderator;
import com.barreirasapp.entities.enums.UserRole;
import com.barreirasapp.infra.annotation.HasRole;
import com.barreirasapp.infra.annotation.HttpMethod;
import com.barreirasapp.infra.annotation.LoginRequired;
import com.barreirasapp.infra.annotation.Route;
import com.barreirasapp.dto.report.RegisterReportDTO;
import com.barreirasapp.dto.report.UpdateReportDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.entities.Report;
import com.barreirasapp.entities.User;
import com.barreirasapp.entities.enums.BarrierType;
import com.barreirasapp.entities.enums.EnvironmentType;
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


@WebServlet("/report/*")
public class ReportController extends Controller {
    private Map<String, RouteInfo> routes;
    private BarrierScenarioService barrierScenarioService;
    private ReportService service;

    @Override
    public void init() throws ServletException {
        super.init();
        this.service = this.getServiceFactory().getReportService();
        this.barrierScenarioService = this.getServiceFactory().getBarrierScenarioService();
    }

    @Route(value = "index/", method = HttpMethod.GET)
    public void renderList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/report/index.jsp");

        List<Report> reportList = this.service.listAll();

        req.setAttribute("reportList", reportList);

        dispatcher.forward(req, resp);
    }

    @Route(value = "create/", method = HttpMethod.GET_POST)
    public void createReport(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("action", "/report/create/");
        req.setAttribute("method", "POST");
        req.setAttribute("barrierTypeOptions", BarrierType.values());
        req.setAttribute("environmentOptions", EnvironmentType.values());

        String barrierScenario = req.getParameter("barrierScenario");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/report/form.jsp");

        if (req.getMethod().equalsIgnoreCase("GET")) {
            dispatcher.forward(req, resp);
            return;
        }

        String type = req.getParameter("barrierType");
        String environment = req.getParameter("environment");
        String incidentDetails = req.getParameter("incidentDetails");
        String anonymous = req.getParameter("anonymous");
        boolean isAnonymous = anonymous != null && anonymous.equals("true");

        req.setAttribute("barrierType", type);
        req.setAttribute("environment", environment);
        req.setAttribute("incidentDetails", incidentDetails);
        req.setAttribute("barrierScenario", barrierScenario);
        req.setAttribute("anonymous", anonymous);

        try {
            RegisterReportDTO reportDTO = new RegisterReportDTO(
                    environment,
                    incidentDetails,
                    barrierScenario,
                    anonymous,
                    type
            );

            if (!isAnonymous) {
                System.out.println(req.getAttributeNames().toString());
                User reporter = (User) req.getAttribute("user");;
                reportDTO.setReporter(reporter);
            }
            log(req.getAttributeNames().toString());
            log(reportDTO.getReporter().getName());

            this.service.insert(reportDTO);
            resp.sendRedirect("/report/index/");
        } catch (ValidationError e) {
            req.setAttribute("anonymous", isAnonymous ? "true" : "false");
            System.out.println("tentei lançar execeção");

            ControllerDispatcher.sendErrors(e.getErrors(), req);
            dispatcher.forward(req, resp);
        }
    }

    @Route(value = "create/{relatedBarrierScenarioId}/", method = HttpMethod.GET_POST)
    public void createReportByScenario(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        this.createReport(req, resp);
    }

    @LoginRequired
    @HasRole(UserRole.MODERATOR)
    @Route(value = "update/{id}", method = HttpMethod.GET_POST)
    public void updateReport(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, IllegalAccessException, ValidationError {
        String reportId = req.getAttribute("id").toString();
        req.setAttribute("action", "/report/update/" + reportId);
        req.setAttribute("method", "POST");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/report/update.jsp");
        if (req.getMethod().equalsIgnoreCase("GET")) {
            setReportFields(req, reportId);
            dispatcher.forward(req, resp);
            return;
        }

        Moderator reviewer = (Moderator) req.getAttribute("user");

        String comment = req.getParameter("reviewer_comment");
        String isValid = req.getParameter("reviewer_isValid");
        
        try {
            UpdateReportDTO updateReportDTO = new UpdateReportDTO(reportId, isValid, comment, reviewer);
            this.service.update(updateReportDTO);
            resp.sendRedirect("/report/index/");
        } catch (ValidationError e) {
            System.out.println(e.getMessage());
            ControllerDispatcher.sendErrors(e.getErrors(), req);
            dispatcher.forward(req, resp);
        }

        req.setAttribute("reviewer_comment", comment);
        req.setAttribute("reviewer_isValid", isValid);
        
    }

    @LoginRequired
    @Route(value = "delete/{Id}", method = HttpMethod.GET_POST)
    public void deleteReport(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String reportId = req.getAttribute("Id").toString();

        req.setAttribute("method", "POST");
        req.setAttribute("action", "/report/delete/" + reportId);


        try {
            this.service.deleteById(Integer.valueOf(reportId));
        } catch (ValidationError e) {
            System.out.println("deu erro");

        } finally {
            resp.sendRedirect("/report/index/");
        }
    }

    @Route(value = "", method = HttpMethod.GET)
    public void renderPublicList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/public/reportList.jsp");
        req.setAttribute("barrierTypeOptions", BarrierType.values());
        req.setAttribute("scenarios", barrierScenarioService.listAll());

        List<Report> reportList = this.service.listAllValid();

        req.setAttribute("reportList", reportList);

        dispatcher.forward(req, resp);
    }

    public void setReportFields(HttpServletRequest req, String reportId) throws ValidationError {
        Report report = service.findById(Integer.valueOf(reportId)).orElseThrow();

        req.setAttribute("id", report.getAmbient());
        req.setAttribute("incidentDetails", report.getEventDetailing());
        req.setAttribute("relatedScenarioId", report.getBarrierScenario());
        req.setAttribute("anonymous", report.getAnonymousReport());
        req.setAttribute("reporter", report.getReporter());
    }

}