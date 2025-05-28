package com.barreirasapp.controller;

import com.barreirasapp.annotation.HttpMethod;
import com.barreirasapp.annotation.LoginRequired;
import com.barreirasapp.annotation.Route;
import com.barreirasapp.dto.barrierScenario.RegisterBarrierScenarioDTO;
import com.barreirasapp.dto.barrierScenario.RegisterCommentDTO;
import com.barreirasapp.dto.barrierScenario.UpdateBarrierScenarioDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.infra.security.UserContext;
import com.barreirasapp.model.entities.BarrierScenario;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.model.enums.BarrierType;
import com.barreirasapp.service.BarrierScenarioService;
import com.barreirasapp.service.LawService;
import com.barreirasapp.utils.ControllerDispatcher;
import com.barreirasapp.utils.Middleware;
import com.barreirasapp.utils.ParamsParser;
import com.barreirasapp.utils.routes.RouteInfo;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.*;

@MultipartConfig()
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

        String[] barrierTypeParams = req.getParameterValues("barrierType");
        String[] lawParams = req.getParameterValues("law");
        String searchTerm = req.getParameter("searchTerm");

        // Chama o serviço com os parâmetros de filtro
        List<BarrierScenario> scenarioList = service.filterAndSearch(barrierTypeParams, lawParams, searchTerm);

        // Define os atributos para o JSP
        req.setAttribute("barrierTypeOptions", BarrierType.values());
        req.setAttribute("laws", lawService.listAll());
        req.setAttribute("scenarioList", scenarioList);

        dispatcher.forward(req, resp);
    }

    @LoginRequired
    @Route(value = "{barrierScenarioId}/comments/delete/{commentId}", method = HttpMethod.POST)
    public void removeComment(HttpServletRequest req, HttpServletResponse resp) throws NoSuchElementException, ValidationError, IOException {
        Integer barrierScenarioId = ParamsParser.parseInteger(req.getAttribute("barrierScenarioId").toString());
        Integer id = ParamsParser.parseInteger(req.getAttribute("commentId").toString());

        User user = UserContext.getUserFromSession(req).orElseThrow();

        service.removeComment(user, barrierScenarioId, id);

        resp.sendRedirect("/scenario/"+ barrierScenarioId + "/");
    }

    @LoginRequired
    @Route(value = "{barrierScenarioId}/like/", method = HttpMethod.POST)
    public void likeScenario(HttpServletRequest req, HttpServletResponse resp) throws NoSuchElementException, ValidationError, IOException {
        Integer barrierScenarioId = ParamsParser.parseInteger(req.getAttribute("barrierScenarioId").toString());

        User user = UserContext.getUserFromSession(req).orElseThrow();

        service.toogleLike(user, barrierScenarioId);
        resp.sendRedirect("/scenario/"+ barrierScenarioId + "/");
    }

    @LoginRequired
    @Route(value = "{barrierScenarioId}/comments/create/", method = HttpMethod.POST)
    public void addComment(HttpServletRequest req, HttpServletResponse resp) throws NoSuchElementException, ValidationError, IOException {
        Integer id = ParamsParser.parseInteger(req.getAttribute("barrierScenarioId").toString());

        String content = req.getParameter("comment-content");
        User author = UserContext.getUserFromSession(req).orElseThrow();

        RegisterCommentDTO commentDTO = new RegisterCommentDTO(content, author, id);

        service.addComment(commentDTO);
        resp.sendRedirect("/scenario/"+ id + "/");
    }

    @Route(value = "{id}/", method = HttpMethod.GET)
    public void renderScenario(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/public/scenario.jsp");

        Integer barrierScenarioId = ParamsParser.parseInteger(req.getAttribute("id").toString());
        BarrierScenario post = service.findById(barrierScenarioId).orElseThrow();
        String userId = UserContext.getCookieValue(req.getCookies(), "userId");

        boolean isLikedByUser = false;

        try {
            int id = Integer.parseInt(userId);
            isLikedByUser = post.isLikedByUser(id);
        } catch (Exception ignored) {}

        req.setAttribute("isLikedByUser", isLikedByUser);
        req.setAttribute("post", post);
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
    public void createBarrierScenario(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ValidationError {
        req.setAttribute("action", "/scenario/create/");
        req.setAttribute("method", "POST");
        req.setAttribute("lawOptions", lawService.listAll());
        req.setAttribute("barrierTypeOptions", BarrierType.values());


        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/barrierScenario/form.jsp");

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

        User author = UserContext.getUserFromSession(req).orElseThrow();

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
        } finally {
            dispatcher.forward(req, resp);
        }
    }

    @LoginRequired
    @Route(value = "update/{barrierScenarioId}/", method = HttpMethod.GET_POST)
    public void updateBarrierScenario(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ValidationError {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/barrierScenario/form.jsp");
        System.out.println("Entrou aqui controller update");

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