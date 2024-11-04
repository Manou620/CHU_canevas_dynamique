package com.chu.canevas.service;

import com.chu.canevas.dto.Personnel.PresentPersonnelDto;
import com.chu.canevas.dto.Scan.EntryDTO;
import com.chu.canevas.dto.Scan.SortieDTO;
import com.chu.canevas.model.Personnel;
import com.chu.canevas.model.Scan;

import java.util.List;

public interface ScanService {

    Scan getLastScanRegistered(Personnel personnel);
    EntryDTO registerEntry(String personnel_IM, Long user_id);
    SortieDTO registerSortie(String personnel_IM, Long user_id);
    List<PresentPersonnelDto> getPresentPersonnel();
}
