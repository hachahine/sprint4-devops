package br.com.mottu.challange.domain.entity;

import br.com.mottu.challange.domain.dto.yard.YardDTO;
import br.com.mottu.challange.domain.dto.yard.YardDetailsDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_yards")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Yard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "yard_seq_generator")
    @SequenceGenerator(name = "yard_seq_generator", sequenceName = "yard_seq", allocationSize = 1)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_unit")
    private Unit unit;

    public Yard(Long idYard) {
        this.id = idYard;
    }

    public Yard(YardDTO yardDTO) {
        this.name = yardDTO.name();
        this.unit = new Unit(yardDTO.idUnit());
    }

    public void update(@Valid YardDetailsDTO yardDetailsDTO) {
        if (yardDetailsDTO.name() != null) {
            this.name = yardDetailsDTO.name();
        }
        if (yardDetailsDTO.idUnit() != null) {
            this.unit = new Unit(yardDetailsDTO.idUnit());
        }
    }
}
