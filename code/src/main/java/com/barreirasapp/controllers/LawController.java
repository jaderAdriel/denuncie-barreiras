package com.barreirasapp.controllers;

import com.barreirasapp.infra.annotation.HttpMethod;
import com.barreirasapp.infra.annotation.LoginRequired;
import com.barreirasapp.infra.annotation.Route;
import com.barreirasapp.dto.law.RegisterLawDTO;
import com.barreirasapp.dto.law.UpdateLawDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.factory.ServiceFactory;
import com.barreirasapp.entities.Law;
import com.barreirasapp.services.LawService;
import com.barreirasapp.utils.ControllerDispatcher;
import com.barreirasapp.infra.Middleware;
import com.barreirasapp.utils.routes.RouteInfo;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@WebServlet("/law/*")
public class LawController extends Controller {
    private Map<String, RouteInfo> routes;

     private LawService lawService;

    @Override
    public void init() throws ServletException {
        super.init();
        ServiceFactory serviceFactory = super.getServiceFactory();
        lawService = serviceFactory.getLawService();
        this.routes = Middleware.setupRoutes(this.getClass());
    }

    @Route(value = "index/", method = HttpMethod.GET)
    public void renderList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/law/index.jsp");

        List<Law> lawList = this.lawService.listAll();

        req.setAttribute("lawList", lawList);

        dispatcher.forward(req, resp);
    }

    @LoginRequired
    @Route(value = "create/", method = HttpMethod.GET_POST)
    public void createLaw(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("action", "/law/create/");
        req.setAttribute("method", "POST");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/law/form.jsp");

        if (req.getMethod().equalsIgnoreCase("GET")) {
            dispatcher.forward(req, resp);
            return;
        }

        String code = req.getParameter("code");
        String date = req.getParameter("date");
        String officialLink = req.getParameter("officialLink");
        String title = req.getParameter("title");
        String description = req.getParameter("description");

        req.setAttribute("code", code);
        req.setAttribute("date", date);
        req.setAttribute("officialLink", officialLink);
        req.setAttribute("title", title);
        req.setAttribute("description", description);

        try {
            RegisterLawDTO registerLawDTO = new RegisterLawDTO(code, title, description, officialLink, date);
            this.lawService.insert(registerLawDTO);
            resp.sendRedirect("/law/index/");
        } catch (ValidationError e) {
            ControllerDispatcher.sendErrors(e.getErrors(), req);
            dispatcher.forward(req, resp);
        }
    }

    @LoginRequired
    @Route(value = "update/{code}", method = HttpMethod.GET_POST)
    public void updateLaw(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String lawCode = req.getAttribute("code").toString();
        req.setAttribute("action", "/law/update/" + lawCode);
        req.setAttribute("method", "PUT");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/law/form.jsp");
        if (req.getMethod().equalsIgnoreCase("GET")) {
            setLawFields(req, lawCode);
            dispatcher.forward(req, resp);
            return;
        }

        String newOfficialLink = req.getParameter("officialLink");
        String newDescription = req.getParameter("description");

        System.out.println("newofficialLink=" + newOfficialLink);
        System.out.println("newDescription=" + newDescription);
        System.out.println("code=" + lawCode);

        try {
            UpdateLawDTO updateLawDTO = new UpdateLawDTO(lawCode, newDescription, newOfficialLink);
            this.lawService.update(updateLawDTO);
            resp.sendRedirect("/law/index/");
            System.out.println("Tecnicamente deu tudo certo");
        } catch (ValidationError e) {
            System.out.println(e.getMessage());
            setLawFields(req, lawCode);
            ControllerDispatcher.sendErrors(e.getErrors(), req);
            dispatcher.forward(req, resp);
        }

        req.setAttribute("officialLink", newOfficialLink);
        req.setAttribute("description", newDescription);
    }

    @LoginRequired
    @Route(value = "delete/{code}", method = HttpMethod.GET_POST)
    public void deleteLaw(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String lawCode = req.getAttribute("code").toString();

        req.setAttribute("method", "DELETE");
        req.setAttribute("action", "/law/delete/" + lawCode);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/law/form.jsp");
        if (req.getMethod().equalsIgnoreCase("GET")) {
            setLawFields(req, lawCode);
            dispatcher.forward(req, resp);
            return;
        }

        try {
            this.lawService.deleteById(lawCode);
        } catch (ValidationError e) {
            resp.sendRedirect("/law/index/");
            dispatcher.forward(req, resp);
        } finally {
            resp.sendRedirect("/law/index/");
            dispatcher.forward(req, resp);
        }
    }


    public void setLawFields(HttpServletRequest req, String lawCode) {

        Optional<Law> lawOptional =  lawService.findById(lawCode);

        if (lawOptional.isEmpty()) {return;}

        Law law =lawOptional.get();

        req.setAttribute("code", law.getCode());
        req.setAttribute("date", law.getDate());
        req.setAttribute("officialLink", law.getOfficialLink());
        req.setAttribute("title", law.getTitle());
        req.setAttribute("description", law.getDescription());
    }

}