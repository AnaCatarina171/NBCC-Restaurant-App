package ca.nbcc.restapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import ca.nbcc.restapp.model.Employee;

public interface EmployeeJpaRepo extends JpaRepository<Employee, Long>{

	Employee findByusername(@Param("username") String username);

}
