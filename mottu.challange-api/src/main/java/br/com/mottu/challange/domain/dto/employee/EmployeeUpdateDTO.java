package br.com.mottu.challange.domain.dto.employee;

import jakarta.validation.constraints.NotNull;

public record EmployeeUpdateDTO(

        @NotNull
        Long id,

        String name,
        String role,
        Long idUnit

) {
}
