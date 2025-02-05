package com.chu.canevas.service;

import com.chu.canevas.dto.Presence.PresenceDTO;
import com.chu.canevas.dto.Scan.AdvancedScanDTO;
import com.chu.canevas.model.Entry;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface AdvancedScanService {
    Page<AdvancedScanDTO> getAdvancedScans (
            Instant startDateRange, Instant endDateRange,
            Short service_id, String immatriculation,
            Long userScannerId, String scanType,
            int size, int page, String sortBy, String sortDirection
    );


    /**
     * Get the list of employees present at a specific instant.
     *
     * @param instant The specific instant.
     * @return List of personnel present at the instant.
     */
    List<PresenceDTO> getPresenceAtInstant (Instant instant);

    /**
     * Get the list of employees present between two instants.
     *
     * @param start The start of the period.
     * @param end   The end of the period.
     * @return List of personnel present during the period.
     */
    List<PresenceDTO> getPresenceBetweenInstants (Instant start, Instant end);
}
