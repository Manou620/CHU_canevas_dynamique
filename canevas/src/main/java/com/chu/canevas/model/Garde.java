package com.chu.canevas.model;

import com.chu.canevas.dto.Garde.GardeDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Garde {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "my_sequence_gen" // Name of the generator
    )
    @SequenceGenerator(name = "my_sequence_gen", // Name of the generator
            sequenceName = "assignation_sequence",// Name of the database sequence
            allocationSize = 1 // Increment size (should match DB sequence increment)
    )
    private Long id_garde;

    private LocalDate date;

    @ManyToOne
    private Service service;

    @ManyToOne
    @JoinColumn(name = "personnel_im")
    private Personnel personnel;

    public Garde(GardeDTO gardeDTO){
        this.date = gardeDTO.getDate();
        if(gardeDTO.getId() !=  null) {
            this.id_garde = gardeDTO.getId();
        }
    }

}
