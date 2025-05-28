package com.barreirasapp.model.dao;

import jakarta.servlet.http.Part;

import java.io.File;
import java.io.OutputStream;

public interface FileDao {
    File insert(Part filePart);
    File getFile(String filename);

    File streamFile(OutputStream output, File file);
}
