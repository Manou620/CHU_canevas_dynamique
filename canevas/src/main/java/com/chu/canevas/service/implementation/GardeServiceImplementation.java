package com.chu.canevas.service.implementation;

import com.chu.canevas.dto.Garde.GardeDTO;
import com.chu.canevas.dto.Planning.GardeCreationDTO;
import com.chu.canevas.exception.ElementNotFoundException;
import com.chu.canevas.model.Garde;
import com.chu.canevas.model.Personnel;
import com.chu.canevas.repository.GardeRepository;
import com.chu.canevas.repository.PersonnelRepository;
import com.chu.canevas.repository.ServiceRepository;
import com.chu.canevas.service.GardeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class GardeServiceImplementation implements GardeService {

    @Autowired
    private GardeRepository gardeRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public List<GardeDTO> saveOrUpdateGardes(List<GardeDTO> gardeList) {
        // Step 1: Map each DTO to a Garde entity
        List<Garde> gardesToSave = gardeList.stream().map(dto -> {
            Garde garde;

            // Check if the DTO contains an existing ID (update case)
            if(dto.getId() != null) {
                // Try to fetch the existing Garde from the database
                garde = gardeRepository.findById(dto.getId()).orElseThrow(
                        () -> new ElementNotFoundException("Garde", dto.getId().toString())
                );
            }else {
                // Create a new Garde for a new record
                garde = new Garde();
            }

            // Step 2: Update fields from the DTO
            garde.setDate(dto.getDate());

            // Fetch related Service entity from the database
            garde.setService(serviceRepository.findById(dto.getServiceId()).orElseThrow(
                    () -> new ElementNotFoundException("Service", dto.getServiceId().toString())
            ));

            // Fetch related Personnel entity from the database
            garde.setPersonnel(personnelRepository.findById(dto.getEmployeeIM()).orElseThrow(
                            () -> new ElementNotFoundException("Employé", dto.getEmployeeIM())
            ));

            return  garde;// Return the fully populated Garde
        }).toList();

        List<Garde> savedGardes = gardeRepository.saveAll(gardesToSave);

        return savedGardes.stream().map( garde -> {
           GardeDTO savedDTO = new GardeDTO(garde);
            return savedDTO;
        }).toList();
    }

    @Override
    public GardeDTO saveOneGarde(GardeDTO gardeDto) {

        Garde garde  = new Garde(gardeDto);
        Personnel personnel = personnelRepository.findById(gardeDto.getEmployeeIM()).orElseThrow(
                () -> new ElementNotFoundException("Personnel", gardeDto.getEmployeeIM())
        );
        if(!Objects.equals(personnel.getService().getId(), gardeDto.getServiceId())){
            throw new RuntimeException("L'employé n'est pas dans le service selectionné");
        }
        com.chu.canevas.model.Service service = serviceRepository.findById(gardeDto.getServiceId()).orElseThrow(
                () -> new ElementNotFoundException("Service", gardeDto.getServiceId().toString())
        );
        garde.setPersonnel(personnel);
        garde.setService(service);

        Garde savedGarde = gardeRepository.save(garde);
        return new GardeDTO(savedGarde);
    }

    @Override
    public List<GardeDTO> getGardesForOneMonthForOneService(Short service_id, int year, int month) {
        return gardeRepository.findGardeByServiceAndMonth(service_id, year, month).stream().map(
                GardeDTO::new
        ).toList();
    }

    @Override
    public void deleteOneGardeEvent(Long id) {
        gardeRepository.deleteById(id);
    }
}
