package com.chu.canevas.model;

import com.chu.canevas.dto.Service.ServiceCreationDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(length = 100, nullable = false, unique = true)
    private String nomService;

    @Column(nullable = true)
    private String description;

    public Service(ServiceCreationDto serviceCreationDto){
        this.nomService = serviceCreationDto.nom_service();
        this.description = serviceCreationDto.description();
    }

    public Service(Short id){
        this.id = id;
    }

    @OneToMany(mappedBy = "service")
    private List<Personnel> personnelList;

}
