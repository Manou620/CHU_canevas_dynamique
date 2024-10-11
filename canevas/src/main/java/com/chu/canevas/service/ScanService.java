package com.chu.canevas.service;

import com.chu.canevas.model.Scan;

public interface ScanService {

    Scan getLastScanRegistered(String personnel_IM);

}
