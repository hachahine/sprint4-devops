package br.com.mottu.challange.domain.entity;

import br.com.mottu.challange.domain.dto.unit.UnitDTO;
import br.com.mottu.challange.domain.dto.unit.UnitDetailsDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_units")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_seq_generator")
    @SequenceGenerator(name = "unit_seq_generator", sequenceName = "unit_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String address;
    private String neighborhood;

    public Unit(Long idUnit) {
        this.id = idUnit;
    }

    public Unit(UnitDTO unitDTO) {
        this.name = unitDTO.name();
        this.address = unitDTO.address();
        this.neighborhood = unitDTO.neighborhood();
    }

    public void update(@Valid UnitDetailsDTO unitDetailsDTO) {
        if (unitDetailsDTO.name() != null) {
            this.name = unitDetailsDTO.name();
        }
        if (unitDetailsDTO.address() != null) {
            this.address = unitDetailsDTO.address();
        }
        if (unitDetailsDTO.neighborhood() != null) {
            this.neighborhood = unitDetailsDTO.neighborhood();
        }
    }

}
