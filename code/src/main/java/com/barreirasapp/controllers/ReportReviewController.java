package com.barreirasapp.controllers;

import com.barreirasapp.dto.RegisterReportReviewDTO;
import com.barreirasapp.entities.Moderator;
import com.barreirasapp.entities.Report;
import com.barreirasapp.entities.User;
import com.barreirasapp.entities.enums.UserRole;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.infra.annotation.HasRole;
import com.barreirasapp.infra.annotation.HttpMethod;
import com.barreirasapp.infra.annotation.LoginRequired;
import com.barreirasapp.infra.annotation.Route;
import com.barreirasapp.services.ReportService;
import com.barreirasapp.utils.ControllerDispatcher;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/report/review/*")
public class ReportReviewController extends Controller {
    private ReportService service;

    @Override
    public void init() throws ServletException {
        super.init();
        this.service = this.getServiceFactory().getReportService();
    }

    @LoginRequired
    @HasRole(UserRole.MODERATOR)
    @Route(value = "{id}/", method = HttpMethod.GET_POST)
    public void reviewReport(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, IllegalAccessException, ValidationError {
        String reportId = req.getAttribute("id").toString();
        req.setAttribute("action", "/report/review/" + reportId + "/");
        req.setAttribute("method", "POST");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/report/review.jsp");

        if (req.getMethod().equalsIgnoreCase("GET")) {
            setReportFields(req, reportId);
            dispatcher.forward(req, resp);
            return;
        }

        User reviewer = (User) req.getAttribute("user");

        String comment = req.getParameter("comment");
        String isValid = req.getParameter("isValid");

        try {
            RegisterReportReviewDTO registerReportReviewDTO = new RegisterReportReviewDTO(comment, isValid, reviewer, reportId);
            service.insertReview(registerReportReviewDTO);
            resp.sendRedirect("/report/index/");
        } catch (ValidationError e) {
            System.out.println(e.getMessage());
            ControllerDispatcher.sendErrors(e.getErrors(), req);
            dispatcher.forward(req, resp);
        }
    }

    @LoginRequired
    @Route(value = "delete/{Id}/", method = HttpMethod.GET_POST)
    public void deleteReport(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String reportId = req.getAttribute("Id").toString();

        this.service.deleteById(Integer.valueOf(reportId));

        resp.sendRedirect("/report/index/");
    }

    public void setReportFields(HttpServletRequest req, String reportId) throws ValidationError {
        Report report = service.findById(Integer.valueOf(reportId)).orElseThrow();

        req.setAttribute("report", report);
        req.setAttribute("review", report.getReview());
    }
}