package br.com.mottu.challange.controller;

import br.com.mottu.challange.domain.dto.motorcycle.MotorcycleDTO;
import br.com.mottu.challange.domain.dto.motorcycle.MotorcycleDetailsDTO;
import br.com.mottu.challange.domain.dto.motorcycle.MotorcycleUpdateDTO;
import br.com.mottu.challange.domain.entity.Motorcycle;
import br.com.mottu.challange.domain.repository.MotorcycleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/motorcycles")
public class MotorcycleController {

    @Autowired
    MotorcycleRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody @Valid MotorcycleDTO motorcycleDTO, UriComponentsBuilder uriBuilder) {
        var motorcycle = new Motorcycle(motorcycleDTO);
        repository.save(motorcycle);

        var uri = uriBuilder.path("/motorcycles/{id}").buildAndExpand(motorcycle.getId()).toUri();

        return ResponseEntity.created(uri).body(new MotorcycleDetailsDTO(motorcycle));
    }

    @GetMapping
    public ResponseEntity<Page<MotorcycleDetailsDTO>> list(@PageableDefault(size = 10, sort = {"id"}) Pageable pageable) {
        var page = repository.findAll(pageable)
                .map(MotorcycleDetailsDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detail(@PathVariable Long id) {
        var motorcycle = repository.getReferenceById(id);
        return ResponseEntity.ok(new MotorcycleDetailsDTO(motorcycle));
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid MotorcycleUpdateDTO motorcycleUpdateDTO) {
        var motorcycle = repository.getReferenceById(motorcycleUpdateDTO.id());
        motorcycle.update(motorcycleUpdateDTO);

        return ResponseEntity.ok(new MotorcycleDetailsDTO(motorcycle));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
