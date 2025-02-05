package com.chu.canevas.specification;

import com.chu.canevas.model.Service;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ServiceSpecification {

    public static Specification<Service> createFullSpecification(
            String id_nom_desc
    ){
        return ((root, query, criteriaBuilder) -> {
           List<Predicate> predicates = new ArrayList<>();
            if(id_nom_desc != null && !id_nom_desc.trim().isEmpty()){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nomService")), '%' + id_nom_desc.toLowerCase() + "%"));
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), '%' + id_nom_desc.toLowerCase() + "%"));
            }
           return predicates.isEmpty() ? criteriaBuilder.conjunction() : criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        });
    }

}
