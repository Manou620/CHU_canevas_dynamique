package com.chu.canevas.service.implementation;

import com.chu.canevas.service.PhotoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class PhotoServiceImpl implements PhotoService {

    //acivate in production
//    @Value("${file.upload-dir}")
//    private String uploadDir;

    //dev version, desactivate in ptoduction
    private final String uploadDir = "uploads/";

    @Override
    public String savePhoto(MultipartFile file, String employee_IM) {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if(Files.notExists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            if(!employee_IM.trim().isEmpty()){
                filename += "_" + employee_IM;
            }

//            Path path = Paths.get(uploadDir + filename);
//            Files.copy(file.getInputStream(), path);

            Path filepath = uploadPath.resolve(filename);

            try(
                    InputStream inputStream = file.getInputStream();
                    OutputStream outputStream = Files.newOutputStream(filepath)
                    ) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1){
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            return filename;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String deletePhoto(String filename){
        Path path = Paths.get(uploadDir + filename);
        try {
            Files.deleteIfExists(path);
            return "Photo supprimé";
        } catch (IOException e) {
            throw new RuntimeException("Erreur de suppression de fichier");
        }
    }
}
