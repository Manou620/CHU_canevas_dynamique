package com.chu.canevas.dto.Planning;

import com.chu.canevas.dto.Personnel.PersonnelLiteDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class gardeDTO {

    private LocalDateTime debut_garde;
    private LocalDateTime fin_garder;
    private LocalDate date_prevu;

}
