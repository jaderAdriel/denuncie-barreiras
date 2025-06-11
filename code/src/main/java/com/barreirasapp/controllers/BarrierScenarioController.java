package com.barreirasapp.controllers;

import com.barreirasapp.entities.enums.UserRole;
import com.barreirasapp.infra.annotation.HasRole;
import com.barreirasapp.infra.annotation.HttpMethod;
import com.barreirasapp.infra.annotation.LoginRequired;
import com.barreirasapp.infra.annotation.Route;
import com.barreirasapp.dto.barrierScenario.RegisterBarrierScenarioDTO;
import com.barreirasapp.dto.barrierScenario.RegisterCommentDTO;
import com.barreirasapp.dto.barrierScenario.UpdateBarrierScenarioDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.entities.BarrierScenario;
import com.barreirasapp.entities.User;
import com.barreirasapp.entities.enums.BarrierType;
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
import java.util.*;

@MultipartConfig()
@WebServlet("/scenario/*")
public class BarrierScenarioController extends Controller {
    private BarrierScenarioService service;
    private LawService lawService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.service = super.getServiceFactory().getBarrierScenarioService();
        this.lawService = super.getServiceFactory().getLawService();
    }

    @LoginRequired
    @HasRole(UserRole.MODERATOR)
    @Route(value = "index/", method = HttpMethod.GET)
    public void renderList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/barrierScenario/index.jsp");
        req.setAttribute("barrierTypeOptions", BarrierType.values());

        List<BarrierScenario> scenarioList = this.service.listAll();

        req.setAttribute("scenarioList", scenarioList);

        dispatcher.forward(req, resp);
    }

    @LoginRequired
    @HasRole(UserRole.MODERATOR)
    @Route(value = "create/", method = HttpMethod.GET_POST)
    public void createBarrierScenario(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ValidationError {
        req.setAttribute("action", "/scenario/create/");
        req.setAttribute("method", "POST");
        req.setAttribute("lawOptions", lawService.listAll());
        req.setAttribute("barrierTypeOptions", BarrierType.values());


        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/scenario/form.jsp");

        if (req.getMethod().equalsIgnoreCase("GET")) {
            dispatcher.forward(req, resp);
            return;
        }

        Part filePart = req.getPart("file");

        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String barrierType = req.getParameter("barrierType");
        String[] associatedLawsIds = req.getParameterValues("associatedLaws");

        req.setAttribute("title", title);
        req.setAttribute("content", content);
        req.setAttribute("barrierType", barrierType);
        req.setAttribute("associatedLaws", associatedLawsIds);

        User author = (User) req.getAttribute("user");;

        try {
            RegisterBarrierScenarioDTO scenarioDTO = new RegisterBarrierScenarioDTO(
                    title,
                    content,
                    barrierType,
                    author,
                    associatedLawsIds,
                    filePart
            );
            this.service.insert(scenarioDTO);
            resp.sendRedirect("/scenario/index/");
        } catch (ValidationError e) {
            ControllerDispatcher.sendErrors(e.getErrors(), req);
            dispatcher.forward(req, resp);
        }
    }

    @LoginRequired
    @HasRole(UserRole.MODERATOR)
    @Route(value = "update/{barrierScenarioId}/", method = HttpMethod.GET_POST)
    public void updateBarrierScenario(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ValidationError {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/scenario/form.jsp");

        int barrierScenarioId = ParamsParser.parseInteger((String) req.getAttribute("barrierScenarioId"));

        req.setAttribute("action", "/scenario/update/" + barrierScenarioId + "/" );
        req.setAttribute("method", "POST" );
        req.setAttribute("lawOptions", lawService.listAll());
        req.setAttribute("barrierTypeOptions", BarrierType.values());

        if (req.getMethod().equalsIgnoreCase("GET")) {
            setBarrierScenarioFields(req, barrierScenarioId);
            dispatcher.forward(req, resp);
            return;
        }

        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String barrierType = req.getParameter("barrierType");
        String[] lawIdsParam = req.getParameterValues("associatedLaws");

        Part filePart = req.getPart("file");

        UpdateBarrierScenarioDTO scenarioDTO = new UpdateBarrierScenarioDTO(
                barrierScenarioId,
                title,
                content,
                barrierType,
                lawIdsParam,
                filePart
        );

        try {
            this.service.update(scenarioDTO);
            resp.sendRedirect("/scenario/index/");
        } catch (ValidationError e) {
            ControllerDispatcher.sendErrors(e.getErrors(), req);
            dispatcher.forward(req, resp);
        }
    }

    @LoginRequired
    @HasRole(UserRole.MODERATOR)
    @Route(value = "delete/{barrierScenarioId}/", method = HttpMethod.GET_POST)
    public void deleteBarrierScenario(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ValidationError {
        Integer barrierScenarioId = ParamsParser.parseInteger((String) req.getAttribute("barrierScenarioId"));

        this.service.delete(barrierScenarioId);

        resp.sendRedirect("/scenario/index/");
    }

    private void setBarrierScenarioFields(HttpServletRequest req, int id) throws ServletException {
        Optional<BarrierScenario> barrierScenario = service.findById(id);

        if (barrierScenario.isEmpty()) {
            throw new ServletException("Cenário barreira não encontrada");
        }

        req.setAttribute("title", barrierScenario.get().getTitle());
        req.setAttribute("content", barrierScenario.get().getContent());
        req.setAttribute("barrierType", barrierScenario.get().getBarrierType());

        String imageName = barrierScenario.get().getImageCoverPath();

        if (imageName != null && !imageName.isBlank()) {
            req.setAttribute("image", "/uploads/report/" + imageName + "/");
        }

        req.setAttribute("associatedLaws", barrierScenario.get().getAssociatedLaws());
    }
}