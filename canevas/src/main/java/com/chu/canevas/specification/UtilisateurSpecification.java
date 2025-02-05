package com.chu.canevas.specification;

import com.chu.canevas.model.Personnel;
import com.chu.canevas.model.Role;
import com.chu.canevas.model.Service;
import com.chu.canevas.model.Utilisateur;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurSpecification {

    public static Specification<Utilisateur> createFullSpecification (
            String id_nom, Long role, Boolean enabled
    ){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicatesAND = new ArrayList<>();
            List<Predicate> predicatesOR = new ArrayList<>();
            if ((role != null)) {
                Join<Utilisateur, Role> serviceJoin = root.join("role");
                predicatesAND.add(criteriaBuilder.equal(serviceJoin.get("id"), role));
            }
            if (id_nom != null && !id_nom.trim().isEmpty()) {
//                predicatesOR.add(criteriaBuilder.equal(root.get("id"), Long.parseLong(id_nom)));
                predicatesOR.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nom_utilisateur")), "%" + id_nom.toLowerCase() + "%"));
            }

            if (enabled != null) {
                predicatesAND.add(criteriaBuilder.equal(root.get("enabled"), enabled));
            }

            // Combine OR predicates
            Predicate orPredicate = predicatesOR.isEmpty() ? criteriaBuilder.conjunction() : criteriaBuilder.or(predicatesOR.toArray(new Predicate[0]));
            // Combine AND predicates
            Predicate andPredicate = predicatesAND.isEmpty() ? criteriaBuilder.conjunction() : criteriaBuilder.and(predicatesAND.toArray(new Predicate[0]));

            return criteriaBuilder.and(orPredicate, andPredicate);
        };
    }

}
