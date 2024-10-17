package com.chu.canevas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "SORTIE")
public class Sortie extends Scan{

    private Boolean pendant_pause;

    @OneToOne
    @JoinColumn(name = "entry_associe")
    private Entry associated_entry;

    private Boolean isEarly;

//    @OneToOne
//    private Autorisation_sortie autorisationSortie;

}
