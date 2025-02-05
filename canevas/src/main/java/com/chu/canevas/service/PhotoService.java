package com.chu.canevas.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {

    String savePhoto(MultipartFile file, String employee_IM);

    String deletePhoto(String filename) ;

}
