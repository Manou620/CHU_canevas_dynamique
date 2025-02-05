package com.chu.canevas.service.implementation;

import com.chu.canevas.dto.Utilisateur.UtilisateurCreationDto;
import com.chu.canevas.dto.Utilisateur.UtilisateurDTO;
import com.chu.canevas.exception.ElementDuplicationException;
import com.chu.canevas.exception.ElementNotFoundException;
import com.chu.canevas.model.Personnel;
import com.chu.canevas.model.Role;
import com.chu.canevas.model.Utilisateur;
import com.chu.canevas.repository.PersonnelRepository;
import com.chu.canevas.repository.RoleRepository;
import com.chu.canevas.repository.UtilisateurRepository;
import com.chu.canevas.service.UtilisateurService;
import com.chu.canevas.specification.UtilisateurSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImplementation implements UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private RoleRepository roleRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * @param utilisateur
     * @return
     */
    @Override
    public Utilisateur registerUser(Utilisateur utilisateur) {
        if(utilisateurRepository.existsByUsername(utilisateur.getNom_utilisateur())){
            throw new ElementDuplicationException("Un utilisateur avec le meme nom existe deja");
        }
        if(utilisateurRepository.existsByPersonnelImmatriculation(utilisateur.getPersonnel().getImmatriculation())){
            throw new ElementDuplicationException("Cet employé possède déja un compte utilisateur");
        }
        utilisateur.setPassword(bCryptPasswordEncoder.encode(utilisateur.getPassword()));
        return utilisateurRepository.save(utilisateur);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Utilisateur getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Utilisateur Introuvable")
        );
    }

    @Override
    public Page<UtilisateurDTO> getUtilisateurs(String id_nom, Long role, Boolean enabled, int page, int size, String sortBy, String sortDirection) {
        Specification<Utilisateur> spec = UtilisateurSpecification.createFullSpecification(
                 id_nom,  role,  enabled
        );

        Sort sort = Sort.unsorted();

        if(sortBy != null && !sortBy.isEmpty() && sortDirection != null && !sortDirection.isEmpty()){
            sort = "DESC".equalsIgnoreCase(sortDirection)
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

//        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();

        Page<Utilisateur> utilisateurs = utilisateurRepository.findAll(spec, pageable);

        Page<UtilisateurDTO> userDtoPage = utilisateurs.map(utilisateur -> new UtilisateurDTO(utilisateur));
        return userDtoPage;
    }

    @Override
    public UtilisateurDTO getUserDtoById(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Utilisateur", id.toString())
        );
        return new UtilisateurDTO(utilisateur);
    }

    @Override
    public UtilisateurDTO updateUser(UtilisateurCreationDto utilisateurCreationDto, Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Utilisateur", id.toString())
        );

        Personnel personnel = personnelRepository.findById(utilisateurCreationDto.getPersonnel_id()).orElseThrow(
                () -> new ElementNotFoundException("Employé", utilisateurCreationDto.getPersonnel_id())
        );

        Role role = roleRepository.findById(utilisateurCreationDto.getRole_id()).orElseThrow(
                () -> new ElementNotFoundException("Role", utilisateurCreationDto.getRole_id().toString())
        );

        utilisateur.setNom_utilisateur(utilisateurCreationDto.getNom_utilisateur());
        utilisateur.setPersonnel(personnel);
        utilisateur.setRole(role);
        utilisateur.setPassword(bCryptPasswordEncoder.encode(utilisateurCreationDto.getPassword()));
        utilisateur.setEnabled(utilisateurCreationDto.getEnabled());

        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        return new UtilisateurDTO(savedUtilisateur);
    }

    @Override
    public UtilisateurDTO updateUsername(String newUsername, Long id) {
        if(utilisateurRepository.existsByUsername(newUsername)){
            throw new ElementDuplicationException("Un utilisateur avec le meme nom existe deja");
        }
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Utilisateur", id.toString())
        );
        utilisateur.setNom_utilisateur(newUsername);
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        return new UtilisateurDTO(savedUtilisateur);
    }

    @Override
    public UtilisateurDTO updatePassword(Long id, String newPassword) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Utilisateur", id.toString())
        );
        utilisateur.setPassword(bCryptPasswordEncoder.encode(utilisateur.getPassword()));
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        return new UtilisateurDTO(savedUtilisateur);
    }

    @Override
    public UtilisateurDTO activateDesactivateUser(Long id, Boolean value) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Utilisateur", id.toString())
        );
        utilisateur.setEnabled(value);
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        return new UtilisateurDTO(savedUtilisateur);
    }

    @Override
    public UtilisateurDTO changeAssociatedEmploye(Long id, String immatriculation) {
        if(utilisateurRepository.existsByPersonnelImmatriculation(immatriculation)){
            throw new ElementDuplicationException("Cet employé possède déja un compte utilisateur");
        }
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Utilisateur", id.toString())
        );
        Personnel personnel = personnelRepository.findById(immatriculation).orElseThrow(
                () -> new ElementNotFoundException("Employé", immatriculation)
        );
        utilisateur.setPersonnel(personnel);
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        return new UtilisateurDTO(savedUtilisateur);
    }

    @Override
    public UtilisateurDTO updateRole(Long id, Long role_id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Utilisateur", id.toString())
        );
        Role role = roleRepository.findById(role_id).orElseThrow(
                () -> new ElementNotFoundException("Role", role_id.toString())
        );
        utilisateur.setRole(role);
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        return new UtilisateurDTO(savedUtilisateur);
    }

}
