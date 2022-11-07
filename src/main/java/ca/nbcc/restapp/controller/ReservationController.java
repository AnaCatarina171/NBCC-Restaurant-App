package ca.nbcc.restapp.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.nbcc.restapp.model.Reservation;
import ca.nbcc.restapp.service.CustomerService;
import ca.nbcc.restapp.service.ReservationService;

@Controller
public class ReservationController {

	ApplicationContext ctx;
	
	private ReservationService rS;
	private CustomerService cS;
	
	//Email Sender Variables
	@Autowired
    private JavaMailSender javaMailSender;
	private String email ="anaaccatarina@gmail.com";
	private String subject;
	private String message;
	
	public ReservationController() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	public ReservationController(ApplicationContext ctx, ReservationService rS, CustomerService cS) {
		super();
		this.ctx = ctx;
		this.rS = rS;
		this.cS = cS;
	}
	
	@GetMapping("reservationOptions")
	public String reservationOptions(Model model) {
		
		return "reservation-options";
	}
	
	@PostMapping("/addNewReservation")
	public String toAddReservation(Model model, @RequestParam("reservationTime") String resTime) {
		
		Reservation reservationToAdd = new Reservation();
		
		//Getting the current day + 1
		LocalDate currentDatePlusOne = LocalDate.now().plusDays(1);
		
		reservationToAdd.setTime(resTime);
		model.addAttribute("reservationToAdd", reservationToAdd);
		model.addAttribute("minDate", currentDatePlusOne);
		
		return "new-reservation";
	}
	
	@PostMapping("processAddReservation")
	public String processAddReservation(Model model, Reservation reservationToAdd, RedirectAttributes ra) {
		
		Reservation addedReservation = rS.addNewReservation(reservationToAdd);
		
		//Formatting the reservation date
		String pattern = "dd MMMM yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String formatDate = simpleDateFormat.format(addedReservation.getDate());
		
		//Email variables
		subject = "New Pending Reservation No. " + addedReservation.getId();
		message = "A new reservation was placed under the name of ... for " + formatDate
		+ " at " + addedReservation.getTime() + ". \n\nGo to /DinningRoom123.ca/pendedReservations to analyze this request.";
		
		//Sending email
		//sendEmail(subject, message, email);
		
		model.addAttribute("addedReservation", addedReservation);
		
		return "reservation-confirmation";
	}
	
	void sendEmail(String subject, String message, String email) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject(subject);
        msg.setText(message);

        javaMailSender.send(msg);

    }
}
