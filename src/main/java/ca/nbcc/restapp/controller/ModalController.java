package ca.nbcc.restapp.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import ca.nbcc.restapp.model.Menu;
import ca.nbcc.restapp.model.Reservation;
import ca.nbcc.restapp.model.ReservationTimes;
import ca.nbcc.restapp.service.MenuService;

import ca.nbcc.restapp.model.RTable;
import ca.nbcc.restapp.model.Reservation;
import ca.nbcc.restapp.service.RTableService;

import ca.nbcc.restapp.service.ReservationService;
import ca.nbcc.restapp.service.ReservationTimeService;

@Controller
@RequestMapping("modals")
public class ModalController {
    
	private ReservationService rS;
	private MenuService ms;
	private RTableService tS;
	private ReservationTimeService rTS;
	
	@Autowired
    public ModalController(ReservationService rS, MenuService ms, RTableService tS, ReservationTimeService rTS) {

		super();
		this.rS = rS;
		this.ms = ms;
		this.tS = tS;
		this.rTS = rTS;
	}
	


	@GetMapping("new-reservation")
    public String goToModal(@RequestParam("reservationTime") String resTime, Model model) {
        
    	Reservation reservationToAdd = new Reservation();

		// Getting the current day + 1
		LocalDate currentDatePlusOne = LocalDate.now().plusDays(1);

		reservationToAdd.setTime(resTime);
		model.addAttribute("reservationToAdd", reservationToAdd);
		model.addAttribute("minDate", currentDatePlusOne);
        
        return "new-reservation";
    }
    
    @GetMapping("add-table-to-res")
    public String goToAddTableToRes(@RequestParam("table") String table, @RequestParam("resNumber") Long resNumber, 
    		@RequestParam("currentPeriod") Long currentPeriod, Model model) throws Exception {
    	
    	ReservationTimes currentPeriodObj = rTS.findReservationById(currentPeriod);
    	
    	Integer tableNumber = Integer.parseInt(table);
    	RTable currentTable = tS.findRTableByNumber((long)tableNumber);
    	
    	Reservation reservation = rS.findReservationById(resNumber);
    	List<Reservation> resOnSameTableSameDay = new ArrayList<>();
    	
    	for (var res : currentTable.getReservations()) {
    		
    		if(res.getDate().equals(reservation.getDate())) {
    			
    			ReservationTimes newPeriod = rTS.findReservationTByTime(res.getTime());
    			
    			if(currentPeriodObj.getResGroup().equals(newPeriod.getResGroup())) {
    				
    				resOnSameTableSameDay.add(res);
    			}
    		}
    	}
    	
    	model.addAttribute("currentPeriod", currentPeriodObj);
    	model.addAttribute("tableNumber", tableNumber);
		model.addAttribute("rToEdit", reservation);
		model.addAttribute("resSameDate", resOnSameTableSameDay);
		
    	return "modal-reservation-table";
    }
    

    @GetMapping("display-menu")
    public String goToModalMenuDisplay(Model model, @RequestParam("menuId") long menuId) {
    	Menu menuToCheck = ms.getMenuById(menuId);
//    	System.out.println(menuToDisplay);
    	model.addAttribute("menuToCheck", menuToCheck);
    	
    	return "modal-menuDisplay";
    }
    

    @GetMapping("show-table-res")
    public String goToTableToRes(@RequestParam("table") String table, Model model) throws Exception {
    	
    	Integer tableNumber = Integer.parseInt(table);
    	RTable currentTable = tS.findRTableByNumber((long)tableNumber);
    	
    	SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
    	String today = sdt.format(new Date());
    	
    	List<Reservation> resOnSameTableSameDay = new ArrayList<>();
    	
    	for (var res : currentTable.getReservations()) {

    		String resDate = sdt.format(res.getDate());
    		
    		if(resDate.equals(today)) {
    			resOnSameTableSameDay.add(res);
    		}
    	}
    	
    	model.addAttribute("tableNumber", tableNumber);
		model.addAttribute("resSameDate", resOnSameTableSameDay);
		
    	return "modal-table-floor-plan";
    }
    
    @GetMapping("setBreakfast")
    public String showBreakfastMenusList(Model model) {
    	List<Menu> breakfastMenus = ms.getAllBreakfastMenu();
    	model.addAttribute("menus",breakfastMenus);
    	model.addAttribute("menuCategory", "Breakfast");
    	return "modal-SetMenu";
    }
    
    @GetMapping("setLunch")
    public String showLunchMenusList(Model model) {
    	List<Menu> lunchMenus = ms.getAllLunchMenu();
    	model.addAttribute("menus",lunchMenus);
    	model.addAttribute("menuCategory", "Lunch");
    	return "modal-SetMenu";
    }
    
    @GetMapping("setEvening")
    public String showEveningMenusList(Model model) {
    	List<Menu> eveningMenus = ms.getAllEveningMenu();
    	model.addAttribute("menus",eveningMenus);
    	model.addAttribute("menuCategory", "Evening");
    	return "modal-SetMenu";
    }

}