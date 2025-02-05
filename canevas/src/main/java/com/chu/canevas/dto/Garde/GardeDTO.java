package com.chu.canevas.dto.Garde;

import com.chu.canevas.model.Garde;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GardeDTO {

    private Long id;

    @Size(min = 6, max = 6, message = "L'immatriculation doit avoir 6 nombre")
    @NotBlank(message = "L'immatriculation ne de doit pas etre null")
    private String employeeIM;

    private String employeeName;

    @NotNull(message = "Le service doit-etre precisé")
    private Short serviceId;

    private String ServiceName;

    @NotNull(message = "La date ne doit pas etre nulle")
    private LocalDate date;

    public GardeDTO (Garde garde){
        this.id = garde.getId_garde();
        this.date = garde.getDate();
        this.serviceId = garde.getService().getId();
        this.ServiceName = garde.getService().getNomService();
        this.employeeIM  =garde.getPersonnel().getImmatriculation();
        this.employeeName = garde.getPersonnel().getNom();
    }

}
