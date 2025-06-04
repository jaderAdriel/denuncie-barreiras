package com.barreirasapp.controllers;

import com.barreirasapp.dto.barrierScenario.RegisterBarrierScenarioDTO;
import com.barreirasapp.dto.barrierScenario.UpdateBarrierScenarioDTO;
import com.barreirasapp.entities.BarrierScenario;
import com.barreirasapp.entities.User;
import com.barreirasapp.entities.enums.BarrierType;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.infra.annotation.HttpMethod;
import com.barreirasapp.infra.annotation.LoginRequired;
import com.barreirasapp.infra.annotation.Route;
import com.barreirasapp.services.BarrierScenarioService;
import com.barreirasapp.services.LawService;
import com.barreirasapp.utils.ControllerDispatcher;
import com.barreirasapp.utils.ParamsParser;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/public/scenario/*")
public class PublicBarrierScenarioController extends Controller {
    private BarrierScenarioService service;
    private LawService lawService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.service = super.getServiceFactory().getBarrierScenarioService();
        this.lawService = super.getServiceFactory().getLawService();
    }

    @Route(value = "", method = HttpMethod.GET)
    public void renderPublicList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/public/scenarioList.jsp");
        req.setAttribute("barrierTypeOptions", BarrierType.values());
        req.setAttribute("laws", lawService.listAll());

        String[] barrierTypeParams = req.getParameterValues("barrierType");
        String[] lawParams = req.getParameterValues("law");
        String searchTerm = req.getParameter("searchTerm");

        List<BarrierScenario> scenarioList = service.filterAndSearch(barrierTypeParams, lawParams, searchTerm);

        req.setAttribute("barrierTypeOptions", BarrierType.values());
        req.setAttribute("laws", lawService.listAll());
        req.setAttribute("scenarioList", scenarioList);

        dispatcher.forward(req, resp);
    }

    @Route(value = "{id}/", method = HttpMethod.GET)
    public void renderScenario(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/public/scenario.jsp");

        Integer barrierScenarioId = ParamsParser.parseInteger(req.getAttribute("id").toString());
        BarrierScenario post = service.findById(barrierScenarioId).orElseThrow();

        User user = (User) req.getAttribute("user");
        if (user != null) {
            req.setAttribute("isLikedByUser", post.isLikedByUser(user.getId()));
        }

        req.setAttribute("post", post);
        req.setAttribute("laws", lawService.listAll());

        dispatcher.forward(req, resp);
    }
}