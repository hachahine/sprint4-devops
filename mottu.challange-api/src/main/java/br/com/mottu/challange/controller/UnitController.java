package br.com.mottu.challange.controller;

import br.com.mottu.challange.domain.dto.unit.UnitDTO;
import br.com.mottu.challange.domain.dto.unit.UnitDetailsDTO;
import br.com.mottu.challange.domain.entity.Unit;
import br.com.mottu.challange.domain.repository.UnitRepository;
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
@RequestMapping("/units")
public class UnitController {

    @Autowired
    UnitRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody @Valid UnitDTO unitDTO, UriComponentsBuilder uriBuilder) {
        var unit = new Unit(unitDTO);
        repository.save(unit);

        var uri = uriBuilder.path("/units/{id}").buildAndExpand(unit.getId()).toUri();

        return ResponseEntity.created(uri).body(new UnitDetailsDTO(unit));
    }

    @GetMapping
    public ResponseEntity<Page<UnitDetailsDTO>> list(@PageableDefault(size = 10, sort = {"id"}) Pageable pageable) {
        var page = repository.findAll(pageable)
                .map(UnitDetailsDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detail(@PathVariable Long id) {
        var unit = repository.getReferenceById(id);
        return ResponseEntity.ok(new UnitDetailsDTO(unit));
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid UnitDetailsDTO unitDetailsDTO) {
        var unit = repository.getReferenceById(unitDetailsDTO.id());
        unit.update(unitDetailsDTO);

        return ResponseEntity.ok(new UnitDetailsDTO(unit));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
