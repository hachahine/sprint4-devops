package br.com.mottu.challange.domain.dto.yard;


import br.com.mottu.challange.domain.entity.Yard;

public record YardDetailsDTO(Long id,
                             String name,
                             Long idUnit) {

    public YardDetailsDTO(Yard yard) {
        this(yard.getId(), yard.getName(), yard.getUnit().getId());
    }

}
