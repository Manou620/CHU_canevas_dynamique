package com.chu.canevas.service.implementation;

import com.chu.canevas.service.PhotoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
            Path path = Paths.get(uploadDir + filename);
            Files.copy(file.getInputStream(), path);
            return filename;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deletePhoto(String filename) throws IOException {
        Path path = Paths.get(uploadDir + filename);
        Files.deleteIfExists(path);
    }
}
