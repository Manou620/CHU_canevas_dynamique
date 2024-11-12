package com.chu.canevas.service.implementation;

import com.chu.canevas.dto.Horaire.HoraireDTO;
import com.chu.canevas.dto.dtoMapper.HoraireDtoMapper;
import com.chu.canevas.exception.ElementNotFoundException;
import com.chu.canevas.model.Horaire;
import com.chu.canevas.repository.HoraireRepository;
import com.chu.canevas.service.HoraireService;
import com.chu.canevas.specification.HoraireSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class HoraireServiceImpl implements HoraireService {

    @Autowired
    private HoraireRepository horaireRepository;

    @Autowired
    private HoraireDtoMapper horaireDtoMapper;

    @Override
    public List<HoraireDTO> getHoraires(Short id_horaire, String libelle_horaire, LocalTime debut_horaire, LocalTime fin_horaire, Boolean flexible, int page, int size, String sortBy, String sortDirection) {
        Specification<Horaire> specification = HoraireSpecification.createFullSpecification(
                id_horaire, libelle_horaire, debut_horaire, fin_horaire, flexible
        );

        Sort sort = Sort.unsorted();

        if(sortBy != null && !sortBy.isEmpty() && sortDirection != null && !sortDirection.isEmpty()){
            sort = "DESC".equalsIgnoreCase(sortDirection)
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        //System.out.println(horaireRepository.findAll(pageable));
        List<Horaire> horaires = horaireRepository.findAll(specification);
        return horaires.stream().map(horaireDtoMapper).toList();
    }

    @Override
    public HoraireDTO getHoraireById(Short id) {
        Horaire horaire  = horaireRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Horaire", id.toString())
        );
        return horaireDtoMapper.apply(horaire);
    }
}
