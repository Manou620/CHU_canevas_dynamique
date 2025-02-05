package com.chu.canevas.dto.dtoMapper;

import com.chu.canevas.dto.Horaire.HoraireLigthDTO;
import com.chu.canevas.dto.Personnel.PersonnelLiteDto;
import com.chu.canevas.dto.Scan.EntryDTO;
import com.chu.canevas.dto.Scan.SortieLightDTO;
import com.chu.canevas.dto.Service.ServiceDTO;
import com.chu.canevas.dto.Utilisateur.UtilisateurInfoDTO;
import com.chu.canevas.model.Entry;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EntryDtoMapper implements Function<Entry, EntryDTO> {
    /**
     * @param entry the function argument 
     * @return
     */
    @Override
    public EntryDTO apply(Entry entry) {

        HoraireLigthDTO horaireLigthDTO = new HoraireLigthDTO(
                entry.getType_horaire_attendu(),
                "null"
        );

        SortieLightDTO sortieLightDTO = new SortieLightDTO(
                (entry.getAnswer_sortie() != null) ? entry.getAnswer_sortie().getIdScan() : null,
                (entry.getAnswer_sortie() != null) ? entry.getAnswer_sortie().getDateEnregistrement() : null
        );

        UtilisateurInfoDTO utilisateurInfoDTO = new UtilisateurInfoDTO(
                entry.getUtilisateur().getId(),
                entry.getUtilisateur().getNom_utilisateur()
        );

        PersonnelLiteDto personnelLiteDto = getPersonnelLiteDto(entry);

        return new EntryDTO(
                entry.getIdScan(),
                entry.getDateEnregistrement(),
                entry.getObservation(),
                entry.getFirst_entry(),
                entry.getIs_late(),
                horaireLigthDTO,
                sortieLightDTO,
                utilisateurInfoDTO,
                personnelLiteDto
        );
    }

    private static PersonnelLiteDto getPersonnelLiteDto(Entry entry) {
        ServiceDTO persServiceDTO = new ServiceDTO(
                entry.getPersonnel().getService().getId(),
                entry.getPersonnel().getService().getNomService(),
                entry.getPersonnel().getService().getDescription()
        );

        return new PersonnelLiteDto(
                entry.getPersonnel().getImmatriculation(),
                entry.getPersonnel().getNom(),
                persServiceDTO,
                entry.getPersonnel().getFonction()
        );
    }
}
