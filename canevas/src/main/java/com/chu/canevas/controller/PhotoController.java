package com.chu.canevas.controller;

import com.chu.canevas.dto.Personnel.PersonnelDTO;
import com.chu.canevas.dto.Personnel.QrRequest;
import com.chu.canevas.dto.dtoMapper.PersonnelDtoMapper;
import com.chu.canevas.exception.ElementNotFoundException;
import com.chu.canevas.model.Personnel;
import com.chu.canevas.repository.PersonnelRepository;
import com.chu.canevas.service.PersonnelService;
import com.chu.canevas.service.PhotoService;
import com.chu.canevas.service.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.NoSuchFileException;

@RestController
@RequestMapping("/api/photo")
public class PhotoController {

    @Autowired
    private PersonnelService personnelService;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private PersonnelDtoMapper personnelDtoMapper;

    @Autowired
    private QrCodeService qrCodeService;

    @PostMapping("/upload/{IM}")
    public ResponseEntity<PersonnelDTO> uploadPersonnelPhoto(@PathVariable String IM, @RequestParam("file") MultipartFile file){
        Personnel personnel = personnelRepository.findById(IM).orElseThrow(
                () -> new ElementNotFoundException("Personnel", IM)
        );

        String filename = photoService.savePhoto(file, personnel.getImmatriculation());
        String photoUrl = "uploads/" + filename;

        personnel.setPhotoPath(photoUrl);
        Personnel savedPers = personnelRepository.save(personnel);

        return new ResponseEntity<>(personnelDtoMapper.apply(savedPers), HttpStatus.OK);
    }

    @DeleteMapping("/{photoName}")
    public ResponseEntity<String> deletePhoto (@PathVariable String photoName){
        return new ResponseEntity<>(photoService.deletePhoto(photoName), HttpStatus.OK);
    }

    @PostMapping("/generate-qr")
    public ResponseEntity<byte[]> generateEmployeQRCode(@RequestBody QrRequest qrRequest){
        byte[] qrCodeImage = qrCodeService.generateEmployeQrCode(qrRequest);

        // Set headers for image download
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentDispositionFormData("attachment", "qr-code.png");

        return ResponseEntity.ok().headers(headers).body(qrCodeImage);
    }

}
