package com.barreirasapp.controller;

import com.barreirasapp.annotation.HttpMethod;
import com.barreirasapp.annotation.LoginRequired;
import com.barreirasapp.annotation.Route;
import com.barreirasapp.dto.barrierScenario.RegisterBarrierScenarioDTO;
import com.barreirasapp.dto.barrierScenario.UpdateBarrierScenarioDTO;
import com.barreirasapp.dto.law.RegisterLawDTO;
import com.barreirasapp.dto.law.UpdateLawDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.infra.security.UserContext;
import com.barreirasapp.model.entities.BarrierScenario;
import com.barreirasapp.model.entities.Law;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.model.enums.BarrierType;
import com.barreirasapp.service.BarrierScenarioService;
import com.barreirasapp.service.LawService;
import com.barreirasapp.utils.ControllerDispatcher;
import com.barreirasapp.utils.Middleware;
import com.barreirasapp.utils.Validator;
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

    @Route(value = "", method = HttpMethod.GET)
    public void renderPublicList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/public/scenarioList.jsp");
        req.setAttribute("barrierTypeOptions", BarrierType.values());
        req.setAttribute("laws", lawService.listAll());

        List<BarrierScenario> scenarioList = this.service.listAll();

        req.setAttribute("scenarioList", scenarioList);

        dispatcher.forward(req, resp);
    }

    @Route(value = "{id}/", method = HttpMethod.GET)
    public void renderScenario(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/public/scenario.jsp");

        int id;
        try {
            id = Integer.parseInt(req.getAttribute("id").toString());
            Optional<BarrierScenario> post = service.findById(id);

            if (post.isEmpty()) {
                throw new ServletException("");
            }

            req.setAttribute("post", post.get());

        } catch (Exception e ) {
            throw new ServletException("Valor inválido");
        }

        req.setAttribute("laws", lawService.listAll());

        dispatcher.forward(req, resp);
    }

    @LoginRequired
    @Route(value = "index/", method = HttpMethod.GET)
    public void renderList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/barrierScenario/index.jsp");
        req.setAttribute("barrierTypeOptions", BarrierType.values());

        List<BarrierScenario> scenarioList = this.service.listAll();

        req.setAttribute("scenarioList", scenarioList);

        dispatcher.forward(req, resp);
    }

    @LoginRequired
    @Route(value = "create/", method = HttpMethod.GET_POST)
    public void createBarrierScenario(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("action", "/scenario/create/");
        req.setAttribute("method", "POST");
        req.setAttribute("lawOptions", lawService.listAll());
        req.setAttribute("barrierTypeOptions", BarrierType.values());

        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/barrierScenario/form.jsp");

        if (req.getMethod().equalsIgnoreCase("GET")) {
            dispatcher.forward(req, resp);
            return;
        }

        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String barrierType = req.getParameter("barrierType");
        String[] associatedLawsIds = req.getParameterValues("associatedLaws");


        req.setAttribute("title", title);
        req.setAttribute("content", content);
        req.setAttribute("barrierType", barrierType);
        req.setAttribute("associatedLaws", associatedLawsIds);

        Optional<User> author = UserContext.getUserFromSession(req);
        if (author.isEmpty()) {
            String error = "Não foi possivel encontrar usuario";
            ControllerDispatcher.sendErrors(Map.of("error", error), req);
            dispatcher.forward(req, resp);
            return;
        }

        try {
            RegisterBarrierScenarioDTO scenarioDTO = new RegisterBarrierScenarioDTO(title, content, barrierType, author.get(), associatedLawsIds );
            this.service.insert(scenarioDTO);
            resp.sendRedirect("/scenario/index/");
        } catch (ValidationError e) {
            ControllerDispatcher.sendErrors(e.getErrors(), req);
            dispatcher.forward(req, resp);
        }
    }

    @LoginRequired
    @Route(value = "update/{barrierScenarioId}/", method = HttpMethod.GET_POST)
    public void updateBarrierScenario(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/barrierScenario/form.jsp");

        int id;
        try {
            id = Integer.parseInt(req.getAttribute("barrierScenarioId").toString());
        } catch (NumberFormatException e) {
            throw new ServletException("Valor inválido");
        }

        req.setAttribute("action", "/scenario/update/" + String.valueOf(id) + "/" );
        req.setAttribute("method", "POST" );
        req.setAttribute("lawOptions", lawService.listAll());
        req.setAttribute("barrierTypeOptions", BarrierType.values());

        if (req.getMethod().equalsIgnoreCase("GET")) {
            setBarrierScenarioFields(req, id);
            dispatcher.forward(req, resp);
            return;
        }

        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String barrierType = req.getParameter("barrierType");
        String[] lawIdsParam = req.getParameterValues("associatedLaws");

        try {
            UpdateBarrierScenarioDTO scenarioDTO = new UpdateBarrierScenarioDTO(id, title, content, barrierType, lawIdsParam);
            this.service.update(scenarioDTO);
            resp.sendRedirect("/scenario/index/");
        } catch (ValidationError e) {
            ControllerDispatcher.sendErrors(e.getErrors(), req);
            dispatcher.forward(req, resp);
        }
    }

    private void setBarrierScenarioFields(HttpServletRequest req, int id) throws ServletException {
        Optional<BarrierScenario> barrierScenario = service.findById(id);

        if (barrierScenario.isEmpty()) {
            throw new ServletException("Cenário barreira não encontrada");
        }

        req.setAttribute("title", barrierScenario.get().getTitle());
        req.setAttribute("content", barrierScenario.get().getContent());
        req.setAttribute("barrierType", barrierScenario.get().getBarrierType());
        req.setAttribute("associatedLaws", barrierScenario.get().getAssociatedLaws());
    }
}