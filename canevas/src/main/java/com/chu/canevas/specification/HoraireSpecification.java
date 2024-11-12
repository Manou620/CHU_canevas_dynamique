package com.chu.canevas.specification;

import com.chu.canevas.model.Horaire;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HoraireSpecification {

    public static Specification<Horaire> createFullSpecification(
            Short id_horaire, String libelle_horaire, LocalTime debut_horaire, LocalTime fin_horaire, Boolean flexible
    ){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> precicates = new ArrayList<>();

            if(id_horaire != null && id_horaire >= 0){
                precicates.add(criteriaBuilder.equal(root.get("id_horaire"), id_horaire));
            }
            if(libelle_horaire != null && !libelle_horaire.trim().isEmpty()) {
                precicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("libelle_horaire")), '%' + libelle_horaire + '%'));
            }
            if(debut_horaire != null) {
                precicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("debut_horaire"), debut_horaire));
            }
            if(fin_horaire != null) {
                precicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("fin_horaire"), fin_horaire));
            }
            if(flexible != null){
                precicates.add(criteriaBuilder.equal(root.get("flexible"), flexible));
            }

            return precicates.isEmpty() ? criteriaBuilder.conjunction() : criteriaBuilder.and(precicates.toArray(new Predicate[0]));

        };
    }

}
