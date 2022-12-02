package ca.nbcc.restapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.nbcc.restapp.model.Menu;
import ca.nbcc.restapp.repo.MenuJpaRepo;


@Service
public class MenuService {

	MenuJpaRepo mRepo;

	@Autowired
	public MenuService(MenuJpaRepo mRepo) {
		super();
		this.mRepo = mRepo;
	}

	public List<Menu> getMenusToDisplay() {
		return mRepo.findByToDisplay(true);
	}

	public Menu saveMenu(Menu menu) {
		return mRepo.save(menu);
	}

	public Menu getLastMenu() {
		List<Menu> allMenus = mRepo.findAll();
		
		if(allMenus.size() == 0) {
			return null;
		}
		return allMenus.get(allMenus.size() - 1);

	}

	public List<Menu> getAllMenus() {
		return mRepo.findAll();
	}
	

	public void deleteMenu(long id) {
		// To delete a menu I got to delete all the dishes that it contains
		mRepo.deleteById(id);
	}

	public Menu getMenuById(long mId) {
		// TODO Auto-generated method stub
		return mRepo.findById(mId).get();
	}
	
	public List<Menu> getAllBreakfastMenu(){
		return mRepo.findByTypeAndToDisplay("Breakfast", true);
	}
	
	public Menu getBreakfastMenu() {
		List<Menu> breakfastMenu = mRepo.findByTypeAndToDisplay("Breakfast", true);
		
		if(breakfastMenu.size() == 1) {
			return breakfastMenu.get(0); 
		}else {
			return null;
		}
		
	}
	public List<Menu> getAllLunchMenu(){
		return mRepo.findByTypeAndToDisplay("Lunch", true);
	}
	
	public Menu getLunchMenu() {
		List<Menu> lunchMenu = mRepo.findByTypeAndToDisplay("Lunch", true);
		if(lunchMenu.size() == 1) {
			return lunchMenu.get(0); 
		}else {
			return null;
		}
	}

	public List<Menu> getAllEveningMenu(){
		return mRepo.findByTypeAndToDisplay("Evening", true);
	}
	
	public Menu getEveningMenu() {
		List<Menu> eveningMenu = mRepo.findByTypeAndToDisplay("Evening", true);
		if(eveningMenu.size() == 1) {
			return eveningMenu.get(0); 
		}else {
			return null;
		}
	}
}
