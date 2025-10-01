package br.com.mottu.challange.domain.repository;

import br.com.mottu.challange.domain.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
