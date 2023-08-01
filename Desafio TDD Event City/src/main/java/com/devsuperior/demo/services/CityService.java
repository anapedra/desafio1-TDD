package com.devsuperior.demo.services;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.services.exception.DataBaseException;
import com.devsuperior.demo.services.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityService {

    private final CityRepository cityRepository;
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }


    @Transactional(readOnly = true)
    public List<CityDTO> findAll(){
        List<City> list=cityRepository.findAll();
        return list.stream().map(CityDTO::new).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public CityDTO findById(Long id){
        Optional<City> obj=cityRepository.findById(id);
        City entity=obj.orElseThrow(
                ()-> new ResourceNotFoundException("Id "+id+" not found"));
        return new CityDTO(entity);
    }

    @Transactional
    public CityDTO insert(CityDTO dto) {
        City entity = new City();
        entity.setName(dto.getName());
        entity = cityRepository.save(entity);
        return new CityDTO(entity);
    }

    @Transactional
    public CityDTO update(Long id, CityDTO dto) {
        try {
            City entity = cityRepository.getReferenceById(id);
            entity.setName(dto.getName());
            entity = cityRepository.save(entity);
            return new CityDTO(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!cityRepository.existsById(id)){
            throw new ResourceNotFoundException("Id not found " + id);
        }
        try {
            cityRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }



}
