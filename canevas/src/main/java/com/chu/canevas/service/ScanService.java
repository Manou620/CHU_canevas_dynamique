package com.chu.canevas.service;

import com.chu.canevas.dto.Scan.EntryDTO;
import com.chu.canevas.model.Personnel;
import com.chu.canevas.model.Scan;

public interface ScanService {

    Scan getLastScanRegistered(Personnel personnel);
    EntryDTO registerEntry(String personnel_IM, Long user_id);
}
