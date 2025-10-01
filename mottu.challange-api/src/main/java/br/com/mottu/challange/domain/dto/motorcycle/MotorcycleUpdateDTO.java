package br.com.mottu.challange.domain.dto.motorcycle;

import jakarta.validation.constraints.NotNull;

public record MotorcycleUpdateDTO(

        @NotNull
        Long id,

        String license,
        String chassis,
        String engine,
        Long idDevice

) {
}
