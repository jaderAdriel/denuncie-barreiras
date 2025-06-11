package com.barreirasapp.controllers;

import com.barreirasapp.entities.Entity;
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
import com.barreirasapp.services.EntityService;
import com.barreirasapp.services.ReportService;
import com.barreirasapp.utils.ControllerDispatcher;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/report/*")
public class ReportController extends Controller {
    private ReportService service;
    private EntityService entityService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.service = this.getServiceFactory().getReportService();
        this.entityService = this.getServiceFactory().getEntityService();
    }

    @LoginRequired
    @Route(value = "index/", method = HttpMethod.GET)
    public void renderList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/report/index.jsp");

        List<Report> reportList = new ArrayList<>();

        User user = (User) req.getAttribute("user");
        if (user.getRole().equals(UserRole.COMMON)) {
            reportList = this.service.listAllFromUser(user);
        } else if (user.getRole().equals(UserRole.MODERATOR)) {
            reportList = this.service.listAll();
        }

        req.setAttribute("reportList", reportList);

        dispatcher.forward(req, resp);
    }

    @Route(value = "create/", method = HttpMethod.GET_POST)
    public void createReport(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("action", "/report/create/");
        req.setAttribute("barrierTypeOptions", BarrierType.values());
        req.setAttribute("environmentOptions", EnvironmentType.values());
        req.setAttribute("entityOptions", entityService.listAll());

        for (Entity entity : entityService.listAll()) {
            System.out.println(entity.getName());
        }

        User reporter = (User) req.getAttribute("user");

        String barrierScenario = req.getParameter("barrierScenario");
        String template = "/templates/report/form.jsp";

        if (reporter == null) {
            template = "/templates/public/form-denuncia.jsp";
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher(template);

        if (req.getMethod().equalsIgnoreCase("GET")) {
            dispatcher.forward(req, resp);
            return;
        }

        String type = req.getParameter("barrierType");
        String entityCnpj = req.getParameter("entity_cnpj");
        String environment = req.getParameter("environment");
        String incidentDetails = req.getParameter("incidentDetails");
        String anonymous = req.getParameter("anonymous");
        boolean isAnonymous = anonymous != null && anonymous.equals("true");

        req.setAttribute("barrierType", type);
        req.setAttribute("environment", environment);
        req.setAttribute("incidentDetails", incidentDetails);
        req.setAttribute("barrierScenario", barrierScenario);
        req.setAttribute("anonymous", anonymous);
        req.setAttribute("entity_cnpj", entityCnpj);

        try {
            RegisterReportDTO reportDTO = new RegisterReportDTO( environment, incidentDetails, barrierScenario, anonymous, type, entityCnpj );
            if (!isAnonymous && reporter != null ) {

                reportDTO.setReporter(reporter);
            }

            this.service.insert(reportDTO);

            if (reporter != null && reporter.getRole() == UserRole.MODERATOR) {
                resp.sendRedirect("/report/index/");
            } else {
                resp.sendRedirect("/");
            }

        } catch (ValidationError e) {
            req.setAttribute("anonymous", isAnonymous ? "true" : "false");
            ControllerDispatcher.sendErrors(e.getErrors(), req);
            dispatcher.forward(req, resp);
        }
    }

    @Route(value = "create/{relatedBarrierScenarioId}/", method = HttpMethod.GET_POST)
    public void createReportByScenario(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        this.createReport(req, resp);
    }
}