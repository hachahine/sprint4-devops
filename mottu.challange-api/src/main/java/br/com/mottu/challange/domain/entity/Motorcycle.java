package br.com.mottu.challange.domain.entity;

import br.com.mottu.challange.domain.dto.motorcycle.MotorcycleDTO;
import br.com.mottu.challange.domain.dto.motorcycle.MotorcycleUpdateDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_motorcycles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Motorcycle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "motorcycle_seq_generator")
    @SequenceGenerator(name = "motorcycle_seq_generator", sequenceName = "motorcycle_seq", allocationSize = 1)
    private Long id;

    private String license;
    private String chassis;
    private String engine;
    private String brand;
    private String model;

    @OneToOne
    @JoinColumn(name = "id_device")
    private Device device;

    public Motorcycle(Long idMotorcycle) {
        this.id = idMotorcycle;
    }

    public Motorcycle(MotorcycleDTO motorcycleDTO) {
        this.license = motorcycleDTO.license();
        this.chassis = motorcycleDTO.chassis();
        this.engine = motorcycleDTO.engine();
        this.brand = motorcycleDTO.brand();
        this.model = motorcycleDTO.model();
        if (motorcycleDTO.idDevice() != null) {
            this.device = new Device(motorcycleDTO.idDevice());
        }
    }

    public void update(@Valid MotorcycleUpdateDTO motorcycleUpdateDTO) {
        if (motorcycleUpdateDTO.license() != null) {
            this.license = motorcycleUpdateDTO.license();
        }
        if (motorcycleUpdateDTO.chassis() != null) {
            this.chassis = motorcycleUpdateDTO.chassis();
        }
        if (motorcycleUpdateDTO.engine() != null) {
            this.engine = motorcycleUpdateDTO.engine();
        }
        if (motorcycleUpdateDTO.idDevice() != null) {
            this.device = new Device(motorcycleUpdateDTO.idDevice());
        }
    }
}
