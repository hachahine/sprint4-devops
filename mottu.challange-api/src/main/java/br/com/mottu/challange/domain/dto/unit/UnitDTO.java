package br.com.mottu.challange.domain.dto.unit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UnitDTO(

        @NotNull
        @NotBlank
        String name,

        @NotNull
        @NotBlank
        String address,

        @NotNull
        @NotBlank
        String neighborhood

) {
}
