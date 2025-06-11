package com.barreirasapp.controllers;

import com.barreirasapp.dto.entity.RegisterEntityDTO;
import com.barreirasapp.dto.report.RegisterReportDTO;
import com.barreirasapp.entities.Entity;
import com.barreirasapp.entities.Report;
import com.barreirasapp.entities.User;
import com.barreirasapp.entities.enums.BarrierType;
import com.barreirasapp.entities.enums.EntityType;
import com.barreirasapp.entities.enums.EnvironmentType;
import com.barreirasapp.entities.enums.UserRole;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.infra.annotation.HttpMethod;
import com.barreirasapp.infra.annotation.LoginRequired;
import com.barreirasapp.infra.annotation.Route;
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

@WebServlet("/entity/*")
public class RegisterEntityController extends Controller {
    private EntityService service;
    private ReportService reportService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.service = this.getServiceFactory().getEntityService();
        this.reportService = this.getServiceFactory().getReportService();
    }

    @LoginRequired
    @Route(value = "create/", method = HttpMethod.POST)
    public void createEntity(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("action", "/entity/create/");
        req.setAttribute("types", EntityType.values());

        String template = "/templates/entity/register.jsp";

        RequestDispatcher dispatcher = req.getRequestDispatcher(template);

        if (req.getMethod().equalsIgnoreCase("GET")) {
            dispatcher.forward(req, resp);
            return;
        }

        String name = req.getParameter("name");
        String cnpj = req.getParameter("cnpj");
        String type = req.getParameter("type");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");

        req.setAttribute("name", name);
        req.setAttribute("cnpj", cnpj);
        req.setAttribute("type", type);
        req.setAttribute("address", address);
        req.setAttribute("phone", phone);

        try {
            RegisterEntityDTO entityDTO = new RegisterEntityDTO(
                    name,
                    cnpj,
                    phone,
                    type
            );

            this.service.register(entityDTO);

            resp.sendRedirect("/entity/list/");

        } catch (ValidationError e) {
            ControllerDispatcher.sendErrors(e.getErrors(), req);
            dispatcher.forward(req, resp);
        } catch (IllegalArgumentException e) {
            req.setAttribute("typeError", "Tipo de entidade inválido.");
            dispatcher.forward(req, resp);
        }
    }

    @LoginRequired
    @Route(value = "index/", method = HttpMethod.GET)
    public void renderList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/entity/index.jsp");

        List<Entity> entityList = new ArrayList<>();

        entityList = this.service.listAll();

        req.setAttribute("entities", entityList);

        dispatcher.forward(req, resp);
    }


    @LoginRequired
    @Route(value = "{cnpj}", method = HttpMethod.GET)
    public void renderDetail(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/public/entity.jsp");
        String cnpj = req.getAttribute("cnpj").toString();
        Entity entity = this.service.findByCnpj(cnpj).orElseThrow();

        List<Report> reports = this.reportService.findAllByEntity(cnpj);

        req.setAttribute("entity", entity);
        req.setAttribute("reports", reports);

        dispatcher.forward(req, resp);
    }
}
