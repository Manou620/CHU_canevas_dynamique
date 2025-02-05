package com.chu.canevas.service;

import com.chu.canevas.dto.Personnel.PresentPersonnelDto;
import com.chu.canevas.dto.RealTimeData.EntryExit;
import com.chu.canevas.dto.Scan.EntryDTO;
import com.chu.canevas.dto.Scan.ScanDTO;
import com.chu.canevas.dto.Scan.SortieDTO;
import com.chu.canevas.dto.Scan.SortieResponseDTO;
import com.chu.canevas.model.Personnel;
import com.chu.canevas.model.Scan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

public interface ScanService {

    Scan getLastScanRegistered(Personnel personnel);

    EntryDTO registerEntry(String personnel_IM, Long user_id);

    SortieResponseDTO registerSortie(String personnel_IM, Long user_id);

    //Liste les personnels presents
    List<PresentPersonnelDto> getPresentPersonnel();

    long countLateFirstEntriesOfToday();
    long countEntriesOfToday();
    long countSortiesOfToday();
    long countEarlyExistsOfToday();

    EntryExit getCountedEntryExitOfToday();

//    public Page<ScanDTO> getFilteredScans(
//            String personnelName, Short service, String immatriculation,
//            Instant startDate, Instant endDate, Pageable pageable
//    );

    List<ScanDTO> getLast5ScansOfPersonnel(String personnelId);

    List<ScanDTO> getLast30Scans();
}
