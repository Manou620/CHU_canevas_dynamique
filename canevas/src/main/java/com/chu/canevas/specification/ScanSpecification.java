package com.chu.canevas.specification;

import com.chu.canevas.model.Personnel;
import com.chu.canevas.model.Scan;
import com.chu.canevas.model.Service;
import com.chu.canevas.model.Utilisateur;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ScanSpecification {

    public static Specification<Scan> flterByPersonnelName(String name) {
        return (root, query, cb) -> name != null ? cb.like(cb.lower(root.get("personnel").get("nom")), "%" + name.toLowerCase() + "%")
                : null;
    }

    public static Specification<Scan> filterByService(Short service) {
        return (root, query, cb) -> service != null
                ? cb.equal(root.get("personnel").get("service").get("id"), service)
                : null;
    }

    public static Specification<Scan> filterByImmatriculation(String immatriculation) {
        return (root, query, cb) -> immatriculation != null
                ? cb.equal(root.get("personnel").get("immatriculation"), immatriculation)
                : null;
    }

    public static Specification<Scan> filterByDateRange(Instant startDate, Instant endDate) {
        return (root, query, cb) -> {
            if (startDate != null && endDate != null) {
                return cb.between(root.get("dateEnregistrement"), startDate, endDate);
            } else if (startDate != null) {
                return cb.greaterThanOrEqualTo(root.get("dateEnregistrement"), startDate);
            } else if (endDate != null) {
                return cb.lessThanOrEqualTo(root.get("dateEnregistrement"), endDate);
            }
            return null;
        };
    }

    public static Specification<Scan> createFullSpecification (
            Instant startDateRange, Instant endDateRange,
            Short service_id, String immatriculation,
            Class<? extends Scan> scanType,
            Long userScannerId)
    {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(scanType != null) {
                predicates.add(criteriaBuilder.equal(root.type(), scanType));
            }

            if(immatriculation != null && !immatriculation.trim().isEmpty()){
                Join<Scan, Personnel> joinPersonnel = root.join("personnel");
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(joinPersonnel.get("immatriculation")), immatriculation.toLowerCase()));
            }

            if(service_id != null){
                Join<Scan, Personnel> joinPersonnel = root.join("personnel");
                Join<Personnel, Service> joinService = joinPersonnel.join("service");
                predicates.add(criteriaBuilder.equal(joinService.get("id"), service_id));
            }

            if(userScannerId != null) {
                Join<Scan, Utilisateur> joinUser = root.join("utilisateur");
                predicates.add(criteriaBuilder.equal(joinUser.get("id"), userScannerId));
            }

            if(startDateRange != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dateEnregistrement"), startDateRange));
            }
            if(endDateRange != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dateEnregistrement"), endDateRange));
            }
             return predicates.isEmpty() ? criteriaBuilder.conjunction() : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
