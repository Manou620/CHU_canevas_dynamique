package com.chu.canevas.dto.Scan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SortieResponseDTO {

    private SortieDTO sortieDTO;
    private int status;
    private String message;
    private String sortie_type;

}
