package ca.nbcc.restapp.service;

import java.util.List;

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
	
	@SuppressWarnings("unchecked")
	public List<Dish> getAllDishesPaginated(Pageable  page){
		return (List<Dish>) dishRepo.findAll(page);
	}
}
