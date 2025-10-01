package br.com.mottu.challange.controller;

import br.com.mottu.challange.domain.dto.yard.YardDTO;
import br.com.mottu.challange.domain.dto.yard.YardDetailsDTO;
import br.com.mottu.challange.domain.entity.Yard;
import br.com.mottu.challange.domain.repository.YardRepository;
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
@RequestMapping("/yards")
public class YardController {

    @Autowired
    YardRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody @Valid YardDTO yardDTO, UriComponentsBuilder uriBuilder) {
        var yard = new Yard(yardDTO);
        repository.save(yard);

        var uri = uriBuilder.path("/yards/{id}").buildAndExpand(yard.getId()).toUri();

        return ResponseEntity.created(uri).body(new YardDetailsDTO(yard));
    }

    @GetMapping
    public ResponseEntity<Page<YardDetailsDTO>> list(@PageableDefault(size = 10, sort = {"id"}) Pageable pageable) {
        var page = repository.findAll(pageable)
                .map(YardDetailsDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detail(@PathVariable Long id) {
        var yard = repository.getReferenceById(id);
        return ResponseEntity.ok(new YardDetailsDTO(yard));
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid YardDetailsDTO yardDetailsDTO) {
        var yard = repository.getReferenceById(yardDetailsDTO.id());
        yard.update(yardDetailsDTO);

        return ResponseEntity.ok(new YardDetailsDTO(yard));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
