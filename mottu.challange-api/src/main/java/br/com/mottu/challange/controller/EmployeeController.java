package br.com.mottu.challange.controller;

import br.com.mottu.challange.domain.dto.employee.EmployeeDTO;
import br.com.mottu.challange.domain.dto.employee.EmployeeDetailsDTO;
import br.com.mottu.challange.domain.dto.employee.EmployeeUpdateDTO;
import br.com.mottu.challange.domain.entity.Employee;
import br.com.mottu.challange.domain.repository.EmployeeRepository;
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
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody @Valid EmployeeDTO employeeDTO, UriComponentsBuilder uriBuilder) {
        var employee = new Employee(employeeDTO);
        repository.save(employee);

        var uri = uriBuilder.path("/empoloyees/{id}").buildAndExpand(employee.getId()).toUri();

        return ResponseEntity.created(uri).body(new EmployeeDetailsDTO(employee));
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeDetailsDTO>> list(@PageableDefault(size = 10, sort = {"id"}) Pageable pageable) {
        var page = repository.findAll(pageable)
                .map(EmployeeDetailsDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detail(@PathVariable Long id) {
        var employee = repository.getReferenceById(id);
        return ResponseEntity.ok(new EmployeeDetailsDTO(employee));
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid EmployeeUpdateDTO employeeUpdateDTO) {
        var employee = repository.getReferenceById(employeeUpdateDTO.id());
        employee.update(employeeUpdateDTO);

        return ResponseEntity.ok(new EmployeeDetailsDTO(employee));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
