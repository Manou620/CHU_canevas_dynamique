package com.chu.canevas.specification;

import com.chu.canevas.model.Planning;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PlanningSpecification {

    public static Specification<Planning> createFullSpecification (
        String IM_nom, Short id_service, LocalDateTime debut, LocalDateTime fin
    ){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            List<Predicate> predicatesOr = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("personnel").get("service").get("id"), id_service));
            if(IM_nom != null && !IM_nom.trim().isEmpty()){
                predicatesOr.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("personnel").get("immatriculation")), "%" + IM_nom.toLowerCase() + "%"));
                predicatesOr.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("personnel").get("nom")), "%" + IM_nom.toLowerCase() + "%"));
            }
            if(debut != null && fin != null) {
                predicates.add(criteriaBuilder.between(root.get("debut_heure"), debut, fin));
            }

            Predicate orPredicate = predicatesOr.isEmpty() ? criteriaBuilder.conjunction() : criteriaBuilder.or(predicatesOr.toArray(new Predicate[0]));
            Predicate andPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));

            return criteriaBuilder.and(andPredicate, orPredicate);
        });
    }

}
