package br.com.mottu.challange.domain.repository;

import br.com.mottu.challange.domain.entity.Yard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YardRepository extends JpaRepository<Yard, Long> {
}
