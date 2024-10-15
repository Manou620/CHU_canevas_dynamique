package com.chu.canevas.dto.Scan;

import java.time.Instant;

public record EntryLiteDTO(
        Long id,
        Instant date_enregistrement
) {

}
