package ca.nbcc.restapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	public List<Dish> getAllDishes(){
		return dishRepo.findAll();
	}
	
	public Dish getDishById(long id) {
		if(dishRepo.findById(id).isPresent()) {
			return dishRepo.findById(id).get();
		}
		return null;
	}
	
	public List<Dish> searchDishByName(String searchTerm){
		return dishRepo.findByName(searchTerm);
	}
	
	public Page<Dish> getAllDishesPaginated(Pageable  page){
		return dishRepo.findAll(page);
	}

	public void saveDish(Dish dish) {
		dishRepo.save(dish);		
	}

	public void deleteDish(long dId) {
		dishRepo.deleteById(dId);
	}
}
