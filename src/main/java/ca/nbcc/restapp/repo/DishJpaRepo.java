package ca.nbcc.restapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.nbcc.restapp.model.Dish;

public interface DishJpaRepo extends JpaRepository<Dish, Long> {

}
