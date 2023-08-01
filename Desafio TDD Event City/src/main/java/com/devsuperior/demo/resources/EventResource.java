package com.devsuperior.demo.resources;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.Event;
import com.devsuperior.demo.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/events")
@CrossOrigin(origins = "*",maxAge = 3600)
public class EventResource {

    private final EventService service;
    public EventResource(EventService service) {
        this.service = service;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EventDTO> update(@PathVariable Long id, @RequestBody EventDTO dto) {
        EventDTO newDto = service.update(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

}
