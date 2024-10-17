package com.chu.canevas.dto.dtoMapper;

import com.chu.canevas.dto.Horaire.HoraireLigthDTO;
import com.chu.canevas.dto.Personnel.PersonnelDTO;
import com.chu.canevas.dto.Scan.EntryLiteDTO;
import com.chu.canevas.dto.Scan.SortieDTO;
import com.chu.canevas.dto.Service.ServiceDTO;
import com.chu.canevas.dto.Utilisateur.UtilisateurInfoDTO;
import com.chu.canevas.model.Sortie;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SortieDtoMapper implements Function<Sortie, SortieDTO> {

    @Override
    public SortieDTO apply(Sortie sortie) {

        PersonnelDTO personnelDTO = getPersonnelDTO(sortie);

        HoraireLigthDTO horaireAttendu = new HoraireLigthDTO(
            sortie.getPersonnel().getHoraire().getId_horaire(),
                sortie.getPersonnel().getHoraire().getLibelle_horaire()
        );

        UtilisateurInfoDTO utilisateurInfo = new UtilisateurInfoDTO(
                sortie.getUtilisateur().getId(),
                sortie.getUtilisateur().getNom_utilisateur()
        );

        EntryLiteDTO associatedEntry = new EntryLiteDTO(
          sortie.getAssociated_entry().getId_scan(),
          sortie.getAssociated_entry().getDate_enregistrement()
        );

        return new SortieDTO(
                sortie.getId_scan(),
                sortie.getDate_enregistrement(),
                sortie.getObservation(),
                sortie.getIsEarly(),
                personnelDTO,
                horaireAttendu,
                utilisateurInfo,
                associatedEntry
        );
    }

    private static PersonnelDTO getPersonnelDTO(Sortie sortie) {
        ServiceDTO serviceDTO = new ServiceDTO(
                sortie.getPersonnel().getService().getId(),
                sortie.getPersonnel().getService().getNomService(),
                sortie.getPersonnel().getService().getDescription()
        );

        return new PersonnelDTO(
            sortie.getPersonnel().getImmatriculation(),
            sortie.getPersonnel().getNom(),
            sortie.getPersonnel().getFonction(),
            sortie.getPersonnel().getPhotoPath(),
            serviceDTO
        );
    }
}
