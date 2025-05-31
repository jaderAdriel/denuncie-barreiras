package com.barreirasapp.repositories;

import jakarta.servlet.http.Part;

import java.io.File;
import java.io.OutputStream;

public interface FileRepository {
    File insert(Part filePart);
    File getFile(String filename);

    File streamFile(OutputStream output, File file);
}
