package br.com.mottu.challange.domain.repository;

import br.com.mottu.challange.domain.entity.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {
}
