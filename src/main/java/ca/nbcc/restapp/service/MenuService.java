package ca.nbcc.restapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.nbcc.restapp.model.Dish;
import ca.nbcc.restapp.model.DishCategory;
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
		List<Menu> lastMenuList = mRepo.findTopByOrderByIdDesc();

		if(lastMenuList.size() == 1) {
			return lastMenuList.get(0);
		}else {
			return null;
		}
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

	public List<Menu> getAllBreakfastMenu() {
		return mRepo.findByType("Breakfast");
	}

	public Menu getBreakfastMenu() {
		List<Menu> breakfastMenu = mRepo.findByTypeAndToDisplay("Breakfast", true);
//		System.out.println(breakfastMenu.toString());
		if (breakfastMenu.size() == 1) {
			return breakfastMenu.get(0);
		} else {
			return null;
		}

	}

	public List<Menu> getAllLunchMenu() {
		return mRepo.findByType("Lunch");
	}

	public Menu getLunchMenu() {
		List<Menu> lunchMenu = mRepo.findByTypeAndToDisplay("Lunch", true);
		if (lunchMenu.size() == 1) {
			return lunchMenu.get(0);
		} else {
			return null;
		}
	}

	public List<Menu> getAllEveningMenu() {
		return mRepo.findByType("Evening");
	}

	public Menu getEveningMenu() {
		List<Menu> eveningMenu = mRepo.findByTypeAndToDisplay("Evening", true);
		if (eveningMenu.size() == 1) {
			return eveningMenu.get(0);
		} else {
			return null;
		}
	}

	public void featureMenu(Menu menuToFeature) {
		stopFeaturingOtherMenus(menuToFeature);
		menuToFeature.setToDisplay(true);
		this.saveMenu(menuToFeature);
	}

	private void stopFeaturingOtherMenus(Menu menuToFeature) {
		List<Menu> allMenus = this.getAllMenus();
		String menuType = menuToFeature.getType();
		for (Menu menu : allMenus) {
			if (menu.getType() != null) {
				if (menu.getType().equals(menuType) && menu.getToDisplay()) {
					menu.setToDisplay(false);
					this.saveMenu(menu);
				}
			}
		}
	}
	
}







