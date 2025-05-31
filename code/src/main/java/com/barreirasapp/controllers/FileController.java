package com.barreirasapp.controllers;

import com.barreirasapp.infra.annotation.HttpMethod;
import com.barreirasapp.infra.annotation.LoginRequired;
import com.barreirasapp.infra.annotation.Route;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.repositories.FileRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;

@WebServlet("/uploads/*")
public class FileController extends Controller{
    FileRepository fileRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        fileRepository = getDaoFactory().createFileDao();
    }

    @LoginRequired
    @Route(value = "report/{filename}", method = HttpMethod.GET)
    public void returnFile(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ValidationError {
        String filename = req.getAttribute("filename").toString();

        File file = fileRepository.getFile(filename);

        String mimeType = req.getServletContext().getMimeType(file.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        resp.setContentType(mimeType);

        resp.setContentLengthLong(file.length());
        resp.setHeader("Content-Disposition", "attachment;filename=\"" + file.getName() + "\"");

        fileRepository.streamFile(resp.getOutputStream(), file);
    }
}
