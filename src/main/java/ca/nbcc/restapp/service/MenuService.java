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
}
