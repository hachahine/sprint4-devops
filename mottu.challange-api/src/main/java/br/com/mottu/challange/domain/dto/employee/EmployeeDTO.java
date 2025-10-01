package br.com.mottu.challange.domain.dto.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmployeeDTO(

        @NotNull
        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotNull
        @NotBlank
        String role,

        @NotNull
        Long idUnit

) {
}
