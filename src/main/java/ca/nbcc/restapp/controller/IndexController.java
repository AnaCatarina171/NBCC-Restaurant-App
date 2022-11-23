package ca.nbcc.restapp.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.nbcc.restapp.model.Employee;
import ca.nbcc.restapp.model.Reservation;
import ca.nbcc.restapp.model.ReservationStatus;
import ca.nbcc.restapp.service.ReservationService;

@Controller
public class IndexController {

	private ReservationService rS;
	
	ApplicationContext ctx;
	
	public IndexController(ApplicationContext ctx) {
		super();
		this.ctx = ctx;
	}
	
	@Autowired
	public IndexController(ReservationService rS, ApplicationContext ctx) {
		super();
		this.rS = rS;
		this.ctx = ctx;
	}



	@GetMapping("/index")
	public String goToIndex(Model model, HttpSession session) {
		
		String currentEmp = (String) session.getAttribute("CURRENT_USER");
		//System.out.println(currentEmp);
		model.addAttribute("userName", currentEmp);
		
		return "index";
	}
	
	@GetMapping("/toAboutUs")
	public String goToAboutUs(Model model) {
		
		return "about-us";
	}
	
	@GetMapping("/userPanel")
	public String goToUserPanel(Model model) {
		
		List<Reservation> allReservations = rS.getAllReservation();
		List<Reservation> pendingReservations = new ArrayList<>();
		
		for(var res : allReservations) {
			
			if(res.getStatus() == ReservationStatus.PENDING) {
				pendingReservations.add(res);
			}
		}
		
		model.addAttribute("pendingReservations", pendingReservations);
		
		return "user-panel";
	}
}
