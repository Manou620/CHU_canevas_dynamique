package com.chu.canevas.model;

import jakarta.persistence.Id;

public class AssignationGarde {

    @Id
    private Long id_assignation_garde;

    private Garde garde;

    private Personnel personnel;

    private String role;

}
