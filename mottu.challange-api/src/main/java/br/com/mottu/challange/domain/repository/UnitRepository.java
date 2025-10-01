package br.com.mottu.challange.domain.repository;

import br.com.mottu.challange.domain.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Long> {
}
