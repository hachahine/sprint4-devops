package br.com.mottu.challange.domain.repository;

import br.com.mottu.challange.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
