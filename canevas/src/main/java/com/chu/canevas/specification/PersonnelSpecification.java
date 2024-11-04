package com.chu.canevas.specification;

import com.chu.canevas.model.Personnel;
import com.chu.canevas.model.Service;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PersonnelSpecification {

    public static Specification<Personnel> createFullSpecification(
            String IM_nom_function, String sexe, Short id_service
    ){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            List<Predicate> predicatesOR = new ArrayList<>();
            if(id_service != null) {
                Join<Personnel, Service> serviceJoin = root.join("service");
                predicates.add(criteriaBuilder.equal(serviceJoin.get("id"), id_service));
            }
            if(IM_nom_function != null && !IM_nom_function.isEmpty()) {
                predicatesOR.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("immatriculation")), "%" + IM_nom_function.toLowerCase() + "%"));
                predicatesOR.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nom")), "%" + IM_nom_function.toLowerCase() + "%"));
                predicatesOR.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("function")), "%" + IM_nom_function.toLowerCase() + "%"));
            }
            if(sexe != null && !sexe.isEmpty()) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("sexe")), sexe.toLowerCase()));
            }

            // Combine OR predicates
            Predicate orPredicate = criteriaBuilder.or(predicatesOR.toArray(new Predicate[0]));
            // Combine AND predicates
            Predicate andPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));

            /// Handle case where both lists are empty
            if(orPredicate == null && andPredicate == null) {
                // Option 1: Return all records (use criteriaBuilder.conjunction())
                return criteriaBuilder.conjunction();

                // Option 2: Return no records (use criteriaBuilder.disjunction())
                // return criteriaBuilder.disjunction();
            }

            if(orPredicate != null && andPredicate != null) {
                return criteriaBuilder.and(orPredicate, andPredicate);
            }else if (orPredicate != null) {
                return orPredicate;
            }else {
                return andPredicate;
            }

        };
    }

}
