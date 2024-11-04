package com.chu.canevas.dto.Personnel;

import com.chu.canevas.dto.Service.ServiceDTO;

public record PersonnelLiteDto(
        String IM,
        String nom,
        ServiceDTO service,
        String fonction
) {
}
