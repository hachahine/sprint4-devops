package br.com.mottu.challange.domain.dto.motorcycle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MotorcycleDTO(

        @NotNull
        @NotBlank
        String license,

        @NotNull
        @NotBlank
        String chassis,

        @NotNull
        @NotBlank
        String engine,

        @NotNull
        @NotBlank
        String brand,

        @NotNull
        @NotBlank
        String model,

        Long idDevice

) {
}
