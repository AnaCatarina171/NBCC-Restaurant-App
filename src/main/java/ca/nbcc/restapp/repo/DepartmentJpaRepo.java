package ca.nbcc.restapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.nbcc.restapp.model.Department;

public interface DepartmentJpaRepo extends JpaRepository<Department, Long>{

	List<Department> findByNameContainingIgnoreCase(String searchTerm);

}
