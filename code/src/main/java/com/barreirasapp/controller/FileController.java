package com.barreirasapp.controller;

import com.barreirasapp.annotation.HttpMethod;
import com.barreirasapp.annotation.LoginRequired;
import com.barreirasapp.annotation.Route;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.FileDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;

@WebServlet("/uploads/*")
public class FileController extends Controller{
    FileDao fileDao;

    @Override
    public void init() throws ServletException {
        super.init();
        fileDao = DaoFactory.createFileDao();
    }

    @LoginRequired
    @Route(value = "report/{filename}", method = HttpMethod.GET)
    public void returnFile(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ValidationError {
        String filename = req.getAttribute("filename").toString();

        File file = fileDao.getFile(filename);

        String mimeType = req.getServletContext().getMimeType(file.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        resp.setContentType(mimeType);

        resp.setContentLengthLong(file.length());
        resp.setHeader("Content-Disposition", "attachment;filename=\"" + file.getName() + "\"");

        fileDao.streamFile(resp.getOutputStream(), file);
    }
}
