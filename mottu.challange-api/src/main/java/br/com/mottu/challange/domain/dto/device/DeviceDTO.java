package br.com.mottu.challange.domain.dto.device;

import br.com.mottu.challange.domain.entity.StatusColor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeviceDTO(

        @NotNull
        @NotBlank
        String code,

        @NotNull
        StatusColor statusColor,

        Boolean active,

        @NotNull
        Long idYard,

        Long idMotorcycle

) {
}
