package com.chu.canevas.dto.Planning;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GardeCreationDTO {

    private LocalDate date;
    private String immatriculation;
    private Short id_service;
    private String avant_garde;
    private boolean isGarde;

}
