package com.chu.canevas.service.implementation;

import com.chu.canevas.dto.Absence.AbsenceCreationDto;
import com.chu.canevas.dto.Absence.AbsenceDTO;
import com.chu.canevas.dto.dtoMapper.AbsenceDtoMapper;
import com.chu.canevas.exception.*;
import com.chu.canevas.model.Absence;
import com.chu.canevas.model.Personnel;
import com.chu.canevas.model.Type_absence;
import com.chu.canevas.repository.AbsenceRepository;
import com.chu.canevas.repository.NonWorkingDaysRepository;
import com.chu.canevas.repository.PersonnelRepository;
import com.chu.canevas.repository.TypeAbsenceRepository;
import com.chu.canevas.service.AbsenceService;
import com.chu.canevas.utils.AbsenceUtils;
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
    @Autowired
    private NonWorkingDaysRepository nonWorkingDaysRepository;
    @Autowired
    private AbsenceDtoMapper absenceDtoMapper;

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
        boolean hasEffectiveAbsence = absenceRepository.existsEffectiveHolidayBetween(
                absenceCreationDto.interim_IM(),
                absenceCreationDto.debut(),
                absenceCreationDto.fin()
        );
        if (hasEffectiveAbsence){
            throw new InterimNotAvailableException(interim);
        }
        //Sod misy absence mifanidry amnio koa hoanl'e employee
        if(absenceRepository.existsEffectiveHolidayBetween(absenceCreationDto.interim_IM(), absenceCreationDto.debut(), absenceCreationDto.fin())){
            throw new AbsenceSuperpositionException("Superposition de date d'absence");
        }

        //Jerena ny type d'absence
        Type_absence typeAbsence = typeAbsenceRepository.findById(absenceCreationDto.type_id()).orElseThrow(
                () -> new ElementNotFoundException("Type d'absence", absenceCreationDto.type_id().toString())
        );

        Absence absenceTosave = new Absence();
        absenceTosave.setDebut_absence(absenceCreationDto.debut());
        absenceTosave.setFin_absence(absenceCreationDto.fin());
        absenceTosave.setMotif(absenceCreationDto.motif());
        absenceTosave.setPersonnel(personnel);
        absenceTosave.setInterim(interim);
        absenceTosave.setTypeAbsence(typeAbsence);

        Absence savedAbsence;

        //Jerena sod cas exceptionnel
        if(absenceCreationDto.isExceptionalCase()){
            System.out.println("cas exceptionnel, enregistrena ny conge");
            savedAbsence = absenceRepository.save(absenceTosave);
        }else{
            System.out.println("on procede aux longues verifications");
            //sod tsa mahazo an'le type d'absence koa ny personne (feno le max cumul miankina am frequence)
            //Sod mifanindry amina absence hafa
            if(!AbsenceUtils.canTakeAbsenceIndvidually(personnel, typeAbsence, absenceCreationDto.debut(), absenceCreationDto.fin(), absenceRepository, nonWorkingDaysRepository)){
                // reh tsa afaka de mthrow exception
                throw new LeaveBalanceException(personnel);
            }
            savedAbsence = absenceRepository.save(absenceTosave);
            /*
                jerena ny periode ahafahana maka conge mba hirespectena an'le une personne sur quatre (1/4) peut prendre le conge dans un service,
                mety mivadika graph any am react ny donnee haza avy ao
            */
        }

        return absenceDtoMapper.apply(savedAbsence);
    }
}
