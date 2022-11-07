package ca.nbcc.restapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.nbcc.restapp.model.Menu;

public interface MenuJpaRepo extends JpaRepository<Menu, Long>{
	List<Menu> findByToDisplay(Boolean toDisplay);
}
