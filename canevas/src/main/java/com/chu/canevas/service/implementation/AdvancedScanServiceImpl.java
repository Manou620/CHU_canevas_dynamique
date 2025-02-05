package com.chu.canevas.service.implementation;

import com.chu.canevas.dto.Presence.PresenceDTO;
import com.chu.canevas.dto.Scan.AdvancedScanDTO;
import com.chu.canevas.model.Entry;
import com.chu.canevas.model.Scan;
import com.chu.canevas.model.Sortie;
import com.chu.canevas.repository.EntryRepository;
import com.chu.canevas.service.AdvancedScanService;
import com.chu.canevas.specification.ScanSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdvancedScanServiceImpl implements AdvancedScanService {

    @Autowired
    private EntryRepository entryRepository;

//    @Autowired
//    ScanRepository scanRepository;
//
//    private final ScanRepository scanRepository;
//
//    public AdvancedScanServiceImpl(
//            @Qualifier("scanRepository") ScanRepository scanRepository
//    ) {
//        this.scanRepository = scanRepository;
//    }


    ///////////
   // private final CustomScanRepository customScanRepository;

//    @Autowired
//    public AdvancedScanServiceImpl(
//            @Qualifier("customScanRepositoryImplementation")
//            CustomScanRepository customScanRepository
//    ) {
//        this.customScanRepository = customScanRepository;
//    }
    ///////////

    @Override
    public Page<AdvancedScanDTO> getAdvancedScans(
            Instant startDateRange, Instant endDateRange,
            Short service_id, String immatriculation,
            Long userScannerId, String scanType,
            int size, int page, String sortBy, String sortDirection
    ) {
        Class<? extends Scan> scanClass = null;
        if(scanType != null && !scanType.trim().isEmpty()){
            if("ENTRY".equalsIgnoreCase(scanType)){
                scanClass = Entry.class;
            }else{
                scanClass = Sortie.class;
            }
        }

        Specification<Scan> specification = ScanSpecification.createFullSpecification(
                startDateRange, endDateRange, service_id, immatriculation, scanClass, userScannerId
        );

        Sort sort = Sort.unsorted();

        if(sortBy != null && !sortBy.isEmpty() && sortDirection != null && !sortDirection.isEmpty()){
            sort = "DESC".equalsIgnoreCase(sortDirection)
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        //Page<Scan> scans = customScanRepository.findScansBySpecification(specification, pageable);
        Page<Scan> scans = entryRepository.findAll(specification, pageable);

        return scans.map(AdvancedScanDTO::new);
    }

    @Override
    public List<PresenceDTO> getPresenceAtInstant(Instant instant) {
        List<Entry> entries = entryRepository.findPresentAtInstant(instant);

        // Use a Map to group by Personnel and keep only the latest Entry
        Map<String, Entry> mappedEntries = entries.stream()
                .collect( Collectors.toMap(
                        entry -> entry.getPersonnel().getImmatriculation(), // Group by Personnel ID
                        entry -> entry, // Map to the entry
                        (existing, replacement) ->
                                replacement.getDateEnregistrement().isAfter(existing.getDateEnregistrement())
                                ? replacement
                                : existing // Keep the latest entry
                ));

        // Transform the Map values into a list of DTOs
        return mappedEntries.values()
                .stream()
                .map(PresenceDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PresenceDTO> getPresenceBetweenInstants(Instant start, Instant end) {
        List<Entry> entries = entryRepository.findPresentBetweenInstants(start, end);
        return entries.stream()
                .map(PresenceDTO::new)
                .distinct()
                .collect(Collectors.toList());
    }
}
