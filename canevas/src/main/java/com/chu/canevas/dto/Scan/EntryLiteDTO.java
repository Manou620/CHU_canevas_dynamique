package com.chu.canevas.dto.Scan;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EntryLiteDTO
//        Long id,
//        Instant date_enregistrement
{
    private Long id;
    private Instant date_enregistrement;
}
