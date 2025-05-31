package com.barreirasapp.repositories.impl;

import com.barreirasapp.repositories.FileRepository;
import jakarta.servlet.http.Part;

import java.io.*;
import java.nio.file.Paths;
import java.util.UUID;

public class FileRepositoryLocal implements FileRepository {
    private String uploadDir = System.getenv("FILES_DIR");

    public FileRepositoryLocal() {
        String envUploadDir = System.getenv("FILES_DIR");

        // 1. Verificar se a variável de ambiente está definida
        if (envUploadDir == null || envUploadDir.trim().isEmpty()) {
            throw new IllegalArgumentException("Environment variable FILES_DIR is not set or is empty. Please set FILES_DIR to the desired upload directory.");
        }

        File directory = new File(envUploadDir);

        // 2. Criar o diretório se ele não existir
        if (!directory.exists()) {
            // mkdirs() cria todos os diretórios pais necessários também
            if (!directory.mkdirs()) {
                throw new RuntimeException("Failed to create upload directory: " + envUploadDir + ". Check file system permissions.");
            }
        }
        // 3. Verificar se o diretório é realmente um diretório e se é gravável
        if (!directory.isDirectory()) {
            throw new RuntimeException("FILES_DIR path " + envUploadDir + " is not a directory.");
        }
        if (!directory.canWrite()) {
            throw new RuntimeException("Upload directory " + envUploadDir + " is not writable. Check permissions.");
        }

        this.uploadDir = envUploadDir; // Atribui o valor após todas as verificações
        System.out.println("FileDaoLocal initialized. Upload directory: " + this.uploadDir);
    }

    @Override
    public File insert(Part filePart) {
        String[] fileNameSplit =  Paths.get(filePart.getSubmittedFileName()).getFileName().toString().split("\\.");
        String fileExtension = "." + fileNameSplit[fileNameSplit.length - 1];

        String fileName = UUID.randomUUID().toString() + fileExtension;

        File file = new File(uploadDir, fileName);

        try (InputStream input = filePart.getInputStream(); FileOutputStream outputStream = new FileOutputStream(file);){
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return file;
    }

    @Override
    public File getFile(String fileName) {
        return new File(uploadDir, fileName);
    }

    @Override
    public File streamFile(OutputStream output, File file) {
        try (FileInputStream in = new FileInputStream(file);) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }


}
