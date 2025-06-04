package com.barreirasapp.controllers;

import com.barreirasapp.dto.barrierScenario.RegisterBarrierScenarioDTO;
import com.barreirasapp.dto.barrierScenario.RegisterCommentDTO;
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
import java.util.NoSuchElementException;
import java.util.Optional;

@MultipartConfig()
@WebServlet("/scenario/interactions/*")
public class BarrierScenarioInteractionController extends Controller {
    private BarrierScenarioService service;

    @Override
    public void init() throws ServletException {
        super.init();
        this.service = super.getServiceFactory().getBarrierScenarioService();
    }

    @LoginRequired
    @Route(value = "{barrierScenarioId}/like/", method = HttpMethod.POST)
    public void likeScenario(HttpServletRequest req, HttpServletResponse resp) throws NoSuchElementException, ValidationError, IOException {
        Integer barrierScenarioId = ParamsParser.parseInteger(req.getAttribute("barrierScenarioId").toString());

        User user = (User) req.getAttribute("user");;

        service.toogleLike(user, barrierScenarioId);
        resp.sendRedirect("/scenario/"+ barrierScenarioId + "/");
    }

    @LoginRequired
    @Route(value = "{barrierScenarioId}/comments/create/", method = HttpMethod.POST)
    public void addComment(HttpServletRequest req, HttpServletResponse resp) throws NoSuchElementException, ValidationError, IOException {
        Integer id = ParamsParser.parseInteger(req.getAttribute("barrierScenarioId").toString());

        String content = req.getParameter("comment-content");
        User author = (User) req.getAttribute("user");;

        RegisterCommentDTO commentDTO = new RegisterCommentDTO(content, author, id);

        service.addComment(commentDTO);
        resp.sendRedirect("/scenario/"+ id + "/");
    }

    @LoginRequired
    @Route(value = "{barrierScenarioId}/comments/delete/{commentId}", method = HttpMethod.POST)
    public void removeComment(HttpServletRequest req, HttpServletResponse resp) throws NoSuchElementException, ValidationError, IOException {
        Integer barrierScenarioId = ParamsParser.parseInteger(req.getAttribute("barrierScenarioId").toString());
        Integer id = ParamsParser.parseInteger(req.getAttribute("commentId").toString());

        User user = (User) req.getAttribute("user");;

        service.removeComment(user, barrierScenarioId, id);

        resp.sendRedirect("/scenario/"+ barrierScenarioId + "/");
    }
}