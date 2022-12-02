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
import ca.nbcc.restapp.service.MenuService;
import ca.nbcc.restapp.model.RTable;
import ca.nbcc.restapp.model.Reservation;
import ca.nbcc.restapp.service.RTableService;
import ca.nbcc.restapp.service.ReservationService;

@Controller
@RequestMapping("modals")
public class ModalController {
    
	private ReservationService rS;
	private MenuService ms;
	private RTableService tS;

	@Autowired
    public ModalController(ReservationService rS, MenuService ms, RTableService tS) {
		super();
		this.rS = rS;
		this.ms = ms;
		this.tS = tS;
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
    public String goToAddTableToRes(@RequestParam("table") String table, @RequestParam("resNumber") Long resNumber,  Model model) throws Exception {
    	
    	Integer tableNumber = Integer.parseInt(table);
    	RTable currentTable = tS.findRTableByNumber((long)tableNumber);
    	
    	Reservation reservation = rS.findReservationById(resNumber);
    	List<Reservation> resOnSameTableSameDay = new ArrayList<>();
    	
    	for (var res : currentTable.getReservations()) {
    		
    		if(res.getDate().equals(reservation.getDate())) {
    			resOnSameTableSameDay.add(res);
    		}
    	}
    	
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
    public String goToTableToRes(@RequestParam("table") String table,  Model model) throws Exception {
    	
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
}