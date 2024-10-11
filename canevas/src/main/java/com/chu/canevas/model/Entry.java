package com.chu.canevas.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "ENTRY")
public class Entry extends Scan{

    @Column(name = "premier_entree")
    private Boolean first_entry;

    @Column(name = "entree_en_retard")
    private Boolean is_late_entry;

    private Short type_horaire_attendu; //peut aussi determiner une premiere entree ou une entree tard

    @OneToOne(mappedBy = "associated_entry")
    private Sortie answer_sortie;

}
