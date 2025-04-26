package com.barreirasapp.controller.person;

import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.PersonDao;
import com.barreirasapp.model.dao.impl.PersonDaoJDBC;
import com.barreirasapp.model.entities.Person;
import com.barreirasapp.model.entities.valueobjects.Email;
import com.barreirasapp.model.enums.Gender;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(("/api/persons/*"))
public class PersonController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();

        PersonDao personDao = DaoFactory.createPersonDao();

        if (pathInfo == null || pathInfo.equals("/")) {
            // GET /api/persons - List all
            List<Person> persons = personDao.findAll();
            resp.getWriter().write(convertToJson(persons));
        } else {
            // GET /api/persons/{id} - Get by ID
            String id = pathInfo.substring(1);
            Person person = personDao.findById(Integer.valueOf(id));
            if (person != null) {
                resp.getWriter().write(convertToJson(person));
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lê o corpo JSON da requisição
        BufferedReader reader = req.getReader();
        String json = reader.lines().collect(Collectors.joining());

        // Converte o JSON para um Map
        Gson gson = new Gson();
        Map<String, String> body = gson.fromJson(json, new TypeToken<Map<String, String>>(){}.getType());

        // Recupera os campos
        String name = body.get("name");
        String emailStr = body.get("email");
        String dateBirthStr = body.get("date_birth");
        String genderStr = body.get("gender");

        // Validação básica
        if (emailStr == null || emailStr.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "O campo 'email' é obrigatório.");
            return;
        }

        // Cria os objetos
        Email email = new Email(emailStr);
        LocalDate dateBirth = LocalDate.parse(dateBirthStr);
        Gender gender = Gender.valueOf(genderStr);

        Person person = new Person(name, email, dateBirth, gender);
        person.save();

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write("Pessoa criada com sucesso!");
    }


    private String convertToJson(Person person) {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
                .add("id", person.getId())
                .add("name", person.getName())
                .add("email", person.getEmail().value())
                .add("dateBirth", person.getBirthDate().toString())
                .add("gender", person.getGender().name());
        return jsonBuilder.build().toString();
    }

    private String convertToJson(List<Person> persons) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Person person : persons) {
            arrayBuilder.add(convertToJson(person));
        }
        return arrayBuilder.build().toString();
    }

}
