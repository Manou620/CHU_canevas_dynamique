package com.chu.canevas.service;

import com.chu.canevas.dto.Garde.GardeDTO;
import com.chu.canevas.dto.Planning.GardeCreationDTO;

import java.util.List;

public interface GardeService {

    List<GardeDTO> saveOrUpdateGardes (List<GardeDTO> gardeList);
    GardeDTO saveOneGarde (GardeDTO garde);
    List<GardeDTO> getGardesForOneMonthForOneService (Short service_id, int year, int month);
    void deleteOneGardeEvent (Long id);
}
