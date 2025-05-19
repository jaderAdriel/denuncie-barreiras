package com.barreirasapp.controller;

import com.barreirasapp.annotation.HttpMethod;
import com.barreirasapp.annotation.LoginRequired;
import com.barreirasapp.annotation.Route;
import com.barreirasapp.dto.barrierScenario.RegisterBarrierScenarioDTO;
import com.barreirasapp.dto.law.RegisterLawDTO;
import com.barreirasapp.dto.law.UpdateLawDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.infra.security.UserContext;
import com.barreirasapp.model.entities.BarrierScenario;
import com.barreirasapp.model.entities.Law;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.service.BarrierScenarioService;
import com.barreirasapp.service.LawService;
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
import java.util.*;
import java.util.stream.Collectors;


@WebServlet("/scenario/*")
public class BarrierScenarioController extends HttpServlet {
    private Map<String, RouteInfo> routes;

    private BarrierScenarioService service;

    private LawService lawService;

    @Override
    public void init() throws ServletException {
        this.service = new BarrierScenarioService();
        this.lawService = new LawService();
        this.routes = Middleware.setupRoutes(this.getClass());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Middleware.callRoute(this, routes, req, resp);
    }

    @Route(value = "index/", method = HttpMethod.GET)
    public void renderList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/barrierScenario/index.jsp");

        List<BarrierScenario> scenarioList = this.service.listAll();

        req.setAttribute("scenarioList", scenarioList);

        dispatcher.forward(req, resp);
    }

    @LoginRequired
    @Route(value = "create/", method = HttpMethod.GET_POST)
    public void createLaw(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("action", "/scenario/create/");
        req.setAttribute("method", "POST");
        req.setAttribute("lawOptions", lawService.listAll());

        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/barrierScenario/form.jsp");

        if (req.getMethod().equalsIgnoreCase("GET")) {
            dispatcher.forward(req, resp);
            return;
        }

        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String type = req.getParameter("type");
        String lawIdsParam = req.getParameter("associatedLaws");

        System.out.println("lawAssociated " + lawIdsParam);

        Optional<User> author = UserContext.getUserFromSession(req);

        req.setAttribute("title", title);
        req.setAttribute("content", content);
        req.setAttribute("type", type);
        req.setAttribute("associatedLaws", List.of());

        if (author.isEmpty()) {
            String error = "Não foi possivel encontrar usuario";
            ControllerDispatcher.sendErrors(Map.of("error", error), req);
            dispatcher.forward(req, resp);
            return;
        }

        try {
            RegisterBarrierScenarioDTO scenarioDTO = new RegisterBarrierScenarioDTO(title, content, type, author.get(), List.of());
            this.service.insert(scenarioDTO);
            resp.sendRedirect("/scenario/index/");
        } catch (ValidationError e) {
            ControllerDispatcher.sendErrors(e.getErrors(), req);
            dispatcher.forward(req, resp);
        }
    }
}