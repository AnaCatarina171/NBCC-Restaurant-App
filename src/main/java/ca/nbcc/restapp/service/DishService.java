package ca.nbcc.restapp.service;

import org.springframework.stereotype.Service;

import ca.nbcc.restapp.model.Dish;
import ca.nbcc.restapp.repo.DishJpaRepo;

@Service
public class DishService {
	private DishJpaRepo dishRepo;
	
	public DishService(DishJpaRepo dishRepo) {
		this.dishRepo = dishRepo;
	}
	
	public Dish addNewDish(Dish dish) {
		return dishRepo.save(dish);
	}

}
