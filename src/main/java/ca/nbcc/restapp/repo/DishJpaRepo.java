package ca.nbcc.restapp.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ca.nbcc.restapp.model.Dish;

public interface DishJpaRepo extends JpaRepository<Dish, Long> {
	public List<Dish> findByName(String name);
	
	public List<Dish> findAllByName(String name, Pageable pageable);
	
	
} 
