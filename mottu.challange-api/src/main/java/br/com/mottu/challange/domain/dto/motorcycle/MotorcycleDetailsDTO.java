package br.com.mottu.challange.domain.dto.motorcycle;


import br.com.mottu.challange.domain.entity.Motorcycle;

public record MotorcycleDetailsDTO(Long id,
                                   String license,
                                   String chassis,
                                   String engine,
                                   String brand,
                                   String model,
                                   Long idDevice) {

    public MotorcycleDetailsDTO(Motorcycle motorcycle) {
        this(motorcycle.getId(), motorcycle.getLicense(), motorcycle.getChassis(), motorcycle.getEngine(), motorcycle.getBrand(), motorcycle.getModel(), motorcycle.getDevice() != null ? motorcycle.getDevice().getId() : null);
    }

}
