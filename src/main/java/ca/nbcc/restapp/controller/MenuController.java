package ca.nbcc.restapp.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ca.nbcc.restapp.model.Menu;
import ca.nbcc.restapp.service.MenuService;

@Controller
public class MenuController {
	
	private ApplicationContext ctx;
	private MenuService ms;
	
		
	public MenuController(ApplicationContext ctx, MenuService ms) {
		super();
		this.ctx = ctx;
		this.ms = ms;
	}
	
	/*
	 * TO DO: 
	 * Show a list to the user what menus are going to be displayed 
	 */
	
	
	@GetMapping("/customerMenuDisplay")
	public String userMenuPage(Model model) {
		List<Menu> menusToDisplay = new ArrayList<>();
		
		menusToDisplay = ms.getMenusToDisplay();
		System.out.println(menusToDisplay.toString());
		model.addAttribute("menusList", menusToDisplay);
		return "display-userMenu";
	}
	
	@GetMapping("/toMenuPanel")
	public String toMenuPanel(Model model) {		
		return "user-menuPanel";
	}
	
	@GetMapping("/toCreateMenu")
	public String toCreateMenu(Model model) {
		
		Menu menuToCreate = new Menu();
		menuToCreate.setDate(LocalDate.now());
		ms.saveMenu(menuToCreate);
		model.addAttribute("menuToAdd", menuToCreate);
		
		return "user-addMenu";
	}
}
