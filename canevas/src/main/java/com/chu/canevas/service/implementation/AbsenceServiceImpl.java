package com.chu.canevas.service.implementation;

import com.chu.canevas.dto.Absence.AbsenceCreationDto;
import com.chu.canevas.dto.Absence.AbsenceDTO;
import com.chu.canevas.exception.ElementNotFoundException;
import com.chu.canevas.exception.SamePersonAsInterimException;
import com.chu.canevas.model.Personnel;
import com.chu.canevas.model.Type_absence;
import com.chu.canevas.repository.AbsenceRepository;
import com.chu.canevas.repository.PersonnelRepository;
import com.chu.canevas.repository.TypeAbsenceRepository;
import com.chu.canevas.service.AbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbsenceServiceImpl implements AbsenceService {

    @Autowired
    private AbsenceRepository absenceRepository;
    @Autowired
    private PersonnelRepository personnelRepository;
    @Autowired
    private TypeAbsenceRepository typeAbsenceRepository;

    @Override
    public AbsenceDTO registerAbsence(AbsenceCreationDto absenceCreationDto) {

        //Jerena ny personnel
        Personnel personnel = personnelRepository.findById(absenceCreationDto.personnel_IM())
                .orElseThrow(() ->
                        new ElementNotFoundException("Personnel", absenceCreationDto.personnel_IM())
                );


        //Jerena ny interim
        Personnel interim = personnelRepository.findById(absenceCreationDto.interim_IM()).orElseThrow(
                () -> new ElementNotFoundException("Personnel", absenceCreationDto.interim_IM())
        );
        //Tsa mahazo mitovy ny interim sy personnel
        if(personnel == interim){
            throw new SamePersonAsInterimException("Meme personnel pour l'interim");
        }

        //sod conge koa ny interim @ le daty tina hovonjena
        Boolean hasEffectiveAbsence = absenceRepository.existsEffectiveHolidayBetween(
                absenceCreationDto.personnel_IM(),
                absenceCreationDto.debut(),
                absenceCreationDto.fin()
        );

        //Jerena ny type d'absence
        Type_absence typeAbsence = typeAbsenceRepository.findById(absenceCreationDto.type_id()).orElseThrow(
                () -> new ElementNotFoundException("Type d'absence", absenceCreationDto.type_id().toString())
        );
        //Jerena sod cas exceptionnel
        //sod tsa mahazo an'le type d'absence koa ny personne (feno le max cumul miankina am frequence)

        /*
            jerena ny periode ahafahana maka conge mba hirespectena an'le une personne sur quatre peut prendre le conge dans un service,
            mety mivadika graph any am react ny donnee haza avy ao
        */

        //Vahana ny olana histockena ny karazana frequence valide pour le type de conge


        return null;
    }
}
