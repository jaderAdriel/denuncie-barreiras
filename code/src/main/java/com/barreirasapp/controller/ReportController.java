package com.barreirasapp.controller;

import com.barreirasapp.annotation.HttpMethod;
import com.barreirasapp.annotation.LoginRequired;
import com.barreirasapp.annotation.Route;
import com.barreirasapp.dto.report.RegisterReportDTO;
import com.barreirasapp.dto.report.UpdateReportDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.infra.security.UserContext;
import com.barreirasapp.model.entities.BarrierScenario;
import com.barreirasapp.model.entities.Moderator;
import com.barreirasapp.model.entities.Report;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.model.enums.BarrierType;
import com.barreirasapp.model.enums.EnvironmentType;
import com.barreirasapp.service.BarrierScenarioService;
import com.barreirasapp.service.ReportService;
import com.barreirasapp.utils.ControllerDispatcher;
import com.barreirasapp.utils.Middleware;
import com.barreirasapp.utils.routes.RouteInfo;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@WebServlet("/report/*")
public class ReportController extends HttpServlet {
    private Map<String, RouteInfo> routes;
    private BarrierScenarioService barrierScenario;
    private ReportService service;

    @Override
    public void init() throws ServletException {
        this.service = new ReportService();
        this.routes = Middleware.setupRoutes(this.getClass());
        this.barrierScenario = new BarrierScenarioService();
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

    @Route(value = "create/", method = HttpMethod.GET_POST)
    public void createReport(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("action", "/report/create/");
        req.setAttribute("method", "PUT");
        req.setAttribute("barrierScenarioOptions", barrierScenario.listAll());
        req.setAttribute("barrierTypeOptions", BarrierType.values());
        req.setAttribute("environmentOptions", EnvironmentType.values());

        String barrierScenario = req.getParameter("barrierScenario");
        try{
            barrierScenario = req.getAttribute("barrierScenarioId").toString();
        }catch(Exception ignore){

        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/report/form.jsp");

        if (req.getMethod().equalsIgnoreCase("GET")) {
            Optional<User> reporter = UserContext.getUserFromSession(UserContext.getSessionId(req.getCookies()));
            dispatcher.forward(req, resp);
            return;
        }

        String[] barrierScenarioIds = req.getParameterValues("barrierScenarioIds");
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
        req.setAttribute("barrierScenarioIds", barrierScenarioIds);

        System.out.println(anonymous);

        try {
            RegisterReportDTO reportDTO = new RegisterReportDTO(
                    environment,
                    incidentDetails,
                    barrierScenario,
                    anonymous,
                    type
            );

            if (!reportDTO.getAnonymous()) {
                Optional<User> reporter = UserContext.getUserFromSession(req);

                if(reporter.isPresent()){
                    reportDTO.setReporter(reporter.get());
                }else{
                    reportDTO.setAnonymous("true");
                }
            }

            this.service.insert(reportDTO);
            resp.sendRedirect("/report/index/");
        } catch (ValidationError e) {
            req.setAttribute("anonymous", isAnonymous ? "true" : "false");
            ControllerDispatcher.sendErrors(e.getErrors(), req);
            dispatcher.forward(req, resp);
        }
    }

    @Route(value = "create/{barrierScenarioId}", method = HttpMethod.GET_POST)
    public void createReportByScenario(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        this.createReport(req, resp);
    }

    @LoginRequired
    @Route(value = "update/{id}", method = HttpMethod.GET_POST)
    public void updateReport(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, IllegalAccessException {
        String reportId = req.getAttribute("id").toString();
        req.setAttribute("action", "/report/update/" + reportId);
        req.setAttribute("method", "POST");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/report/update.jsp");
        if (req.getMethod().equalsIgnoreCase("GET")) {
            setReportFields(req, reportId);
            dispatcher.forward(req, resp);
            return;
        }

        User user = UserContext.getUserFromSession(req).orElseThrow();

        Moderator reviewer = new Moderator(
                user.getPassword(),
                user.getGender(),
                user.getBirthDate(),
                user.getEmail(),
                user.getName(),
                "77777");

        reviewer.setId(user.getId());

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
        req.setAttribute("scenarios", barrierScenario.listAll());

        List<Report> reportList = this.service.listAll();

        req.setAttribute("reportList", reportList);

        dispatcher.forward(req, resp);
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