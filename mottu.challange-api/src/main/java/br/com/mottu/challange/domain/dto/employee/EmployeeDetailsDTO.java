package br.com.mottu.challange.domain.dto.employee;

import br.com.mottu.challange.domain.entity.Employee;

public record EmployeeDetailsDTO(Long id,
                                 String name,
                                 String email,
                                 String role,
                                 Long idUnit) {

    public EmployeeDetailsDTO(Employee employee) {
        this(employee.getId(), employee.getName(), employee.getEmail(), employee.getRole(), employee.getUnit().getId());
    }

}
