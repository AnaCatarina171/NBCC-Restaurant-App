package ca.nbcc.restapp.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.nbcc.restapp.model.Reservation;
import ca.nbcc.restapp.service.ReservationService;

@Controller
@RequestMapping("modals")
public class ModalController {
    
	private ReservationService rS;
	
	@Autowired
    public ModalController(ReservationService rS) {
		super();
		this.rS = rS;
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
    	
    	Reservation reservation = rS.findReservationById(resNumber);
    	
    	
    	
    	model.addAttribute("tableNumber", tableNumber);
		model.addAttribute("rToEdit", reservation);
		
    	return "modal-reservation-table";
    }
    
    
}