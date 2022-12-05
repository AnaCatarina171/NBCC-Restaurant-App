package ca.nbcc.restapp.controller;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.nbcc.restapp.model.RTable;
import ca.nbcc.restapp.model.Reservation;
import ca.nbcc.restapp.model.ReservationStatus;
import ca.nbcc.restapp.model.ReservationTimeGroup;
import ca.nbcc.restapp.model.ReservationTimes;
import ca.nbcc.restapp.service.CustomerService;
import ca.nbcc.restapp.service.RTableService;
import ca.nbcc.restapp.service.ReservationService;
import ca.nbcc.restapp.service.ReservationTimeService;

@Controller
public class ReservationController {

	ApplicationContext ctx;

	private ReservationService rS;
	private ReservationTimeService rTS;
	private CustomerService cS;
	private RTableService tS;

	// Email Sender Variables
	@Autowired
	private JavaMailSender javaMailSender;
	private String email = "anaaccatarina@gmail.com";
	private String subject;
	private String message;

	public ReservationController() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ReservationController(ApplicationContext ctx, ReservationService rS, CustomerService cS, ReservationTimeService rTS) {
		super();
		this.ctx = ctx;
		this.rS = rS;
		this.cS = cS;
		this.rTS = rTS;
	}
	
	@Autowired
	public ReservationController(ReservationService rS, ReservationTimeService rTS,  RTableService tS) {
		super();
		this.rS = rS;
		this.rTS = rTS;
		this.tS = tS;
	}

	@GetMapping("reservationOptions")
	public String reservationOptions(Model model) {
		
		List<ReservationTimes> resTimeBreakfast = new ArrayList<>();
		List<ReservationTimes> resTimeLunch = new ArrayList<>();
		List<ReservationTimes> resTimeNight = new ArrayList<>();
		
		for(var resT : rTS.getAllReservationTimes()){
			
			if(resT.getResGroup().equals(ReservationTimeGroup.BREAKFAST)) {
				resTimeBreakfast.add(resT);
			} else if(resT.getResGroup().equals(ReservationTimeGroup.LUNCH)) {
				resTimeLunch.add(resT);
			} else if(resT.getResGroup().equals(ReservationTimeGroup.NIGHT)) {
				resTimeNight.add(resT);
			}
		}
		
		model.addAttribute("resTimeBreakfast", resTimeBreakfast);
		model.addAttribute("resTimeLunch", resTimeLunch);
		model.addAttribute("resTimeNight", resTimeNight);
		
		return "reservation-options";
	}
	
	@GetMapping("/yourReservations")
	public String goToYourReservations(Model model) {
		
		return "your-reservations";
	}

	@GetMapping("todayFloorPlan")
	public String todayResFloorPlan(Model model) {

		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
    	String today = sdt.format(new Date());

		List<Reservation> todayReservations = rS.getTodayRes();

		try {

			// Adding Tables - Too much code for now (will try to reduce later)
			RTable t10 = tS.findRTableByNumber((long) 10);
			int t10ResToday = 0;
			RTable t11 = tS.findRTableByNumber((long) 11);
			int t11ResToday = 0;
			RTable t12 = tS.findRTableByNumber((long) 12);
			int t12ResToday = 0;
			RTable t40 = tS.findRTableByNumber((long) 40);
			int t40ResToday = 0;
			RTable t41 = tS.findRTableByNumber((long) 41);
			int t41ResToday = 0;
			RTable t42 = tS.findRTableByNumber((long) 42);
			int t42ResToday = 0;
			RTable t43 = tS.findRTableByNumber((long) 43);
			int t43ResToday = 0;
			RTable t20 = tS.findRTableByNumber((long) 20);
			int t20ResToday = 0;
			RTable t21 = tS.findRTableByNumber((long) 21);
			int t21ResToday = 0;
			RTable t22 = tS.findRTableByNumber((long) 22);
			int t22ResToday = 0;
			RTable t30 = tS.findRTableByNumber((long) 30);
			int t30ResToday = 0;
			RTable t50 = tS.findRTableByNumber((long) 50);
			int t50ResToday = 0;
			RTable t51 = tS.findRTableByNumber((long) 51);
			int t51ResToday = 0;
			RTable t52 = tS.findRTableByNumber((long) 52);
			int t52ResToday = 0;

			for (var tR : t10.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(today)) {
					t10ResToday = 1;
				}
			}
			for (var tR : t11.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(today)) {
					t11ResToday = 1;
				}
			}
			for (var tR : t12.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(today)) {
					t12ResToday = 1;
				}
			}
			for (var tR : t40.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(today)) {
					t40ResToday = 1;
				}
			}
			for (var tR : t41.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(today)) {
					t41ResToday = 1;
				}
			}
			for (var tR : t42.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(today)) {
					t42ResToday = 1;
				}
			}
			for (var tR : t43.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(today)) {
					t43ResToday = 1;
				}
			}
			for (var tR : t20.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(today)) {
					t20ResToday = 1;
				}
			}
			for (var tR : t21.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(today)) {
					t21ResToday = 1;
				}
			}
			for (var tR : t22.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(today)) {
					t22ResToday = 1;
				}
			}
			for (var tR : t30.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(today)) {
					t30ResToday = 1;
				}
			}
			for (var tR : t50.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(today)) {
					t50ResToday = 1;
				}
			}
			for (var tR : t51.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(today)) {
					t51ResToday = 1;
				}
			}
			for (var tR : t52.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(today)) {
					t52ResToday = 1;
				}
			}

			model.addAttribute("todayReservations", todayReservations);

			model.addAttribute("t10ResToday", t10ResToday);
			model.addAttribute("t11ResToday", t11ResToday);
			model.addAttribute("t12ResToday", t12ResToday);
			model.addAttribute("t40ResToday", t40ResToday);
			model.addAttribute("t41ResToday", t41ResToday);
			model.addAttribute("t42ResToday", t42ResToday);
			model.addAttribute("t43ResToday", t43ResToday);
			model.addAttribute("t20ResToday", t20ResToday);
			model.addAttribute("t21ResToday", t21ResToday);
			model.addAttribute("t22ResToday", t22ResToday);
			model.addAttribute("t30ResToday", t30ResToday);
			model.addAttribute("t50ResToday", t50ResToday);
			model.addAttribute("t51ResToday", t51ResToday);
			model.addAttribute("t52ResToday", t52ResToday);

			return "reservation-floor-plan";
		} catch (Exception e) {

			return null;
		}
	}

	@PostMapping("/filterReservations")
	public String orderReservations(Model model, @RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "orderByP", required = false) String orderByP) {

		List<Reservation> allReservations = rS.getAllReservation();
		List<Reservation> pastReservations = rS.pastReservations();

		if (orderBy != null) {
			if (orderBy.equals("newest")) {
				allReservations = rS.orderByDate();
			} else if (orderBy.equals("oldest")) {
				allReservations = rS.orderByDateDesc();
			}
		}
		if (orderByP != null) {
			if (orderByP.equals("newest")) {
				pastReservations = rS.pastReservations();
			} else if (orderByP.equals("oldest")) {
				pastReservations = rS.pastReservationsDesc();
			}
		}

		model.addAttribute("pastReservations", pastReservations);
		model.addAttribute("reservations", allReservations);
		model.addAttribute("orderBySelected", orderBy);
		model.addAttribute("orderBySelectedP", orderByP);

		return "reservations-display";
	}

	@GetMapping("/viewReservations")
	public String goToReservations(Model model, String rId, String pastRId) throws Exception {
		
		try {
			List<Reservation> allReservations = rS.getAllReservation();
			List<Reservation> pastReservations = rS.pastReservations();

			if (rId != null && rId != "" && !rId.isEmpty()) {
				Long rLongId = Long.parseLong(rId);

				allReservations = new ArrayList<>();

				try {
					allReservations.add(rS.findReservationById(rLongId));
				} catch(Exception ex) {
					allReservations = rS.getAllReservation();
				}
			}

			if (pastRId != null && pastRId != "" && !pastRId.isEmpty()) {
				Long rLongId = Long.parseLong(pastRId);

				pastReservations = new ArrayList<>();

				try {
					pastReservations.add(rS.findReservationById(rLongId));
				} catch(Exception ex) {
					pastReservations = rS.pastReservations();
				}
			}

			model.addAttribute("pastReservations", pastReservations);
			model.addAttribute("reservations", allReservations);

			return "reservations-display";
		}
		catch(Exception ex) {
			
			return "reservations-display";
		}
		
	}

	@GetMapping("toReservationAdmin")
	public String toReservationPanel(Model model) {

		List<Reservation> allReservations = rS.getAllReservation();
		List<Reservation> pendingReservations = new ArrayList<>();
		List<Reservation> weekReservations = new ArrayList<>();

		for (var res : allReservations) {

			if (res.getStatus() == ReservationStatus.PENDING) {
				pendingReservations.add(res);
			}
			if (res.getStatus() == ReservationStatus.CONFIRMED) {

				Date resDate = res.getDate();
				LocalDate resLocalDate = resDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				
				LocalDate today = LocalDate.now();
				
				LocalDate monday = today;

				do {
					monday = monday.plusDays(1);
				} while (monday.getDayOfWeek() != DayOfWeek.MONDAY);

				if ((resLocalDate.isEqual(today) || (resLocalDate.isAfter(today)) && resLocalDate.isBefore(monday))) {
					weekReservations.add(res);
					System.out.println(today);
				}
			}
		}

		model.addAttribute("pendingReservations", pendingReservations);
		model.addAttribute("weekReservations", weekReservations);
		model.addAttribute("allReservations", allReservations);

		return "user-admin-reservations";
	}
	
	@GetMapping("/addResTable/{rId}/{tId}")
	public String addResTable(Model model, @PathVariable("rId") String rId, @PathVariable("tId") String tId) throws Exception {
		
		Long resNumber = Long.parseLong(rId);
		Long tableNumber = Long.parseLong(tId);
		RTable table = null;
		
		Reservation reservation = rS.findReservationById(resNumber);
		
		for(var t : tS.getAllRTable()) {
			if(t.getNumber().equals(tableNumber)) {
				table = t;
			}
		}
		
		reservation.setTable(table);
		
		rS.updateReservation(reservation);
		
		return "redirect:/processReservation/" + rId;
	}
	
	@GetMapping("/processReservation/{rId}")
	public String processNewReservation(Model model, @PathVariable("rId") long rId) {

		//Store reservations with same date as current reservation
		List<Reservation> resDateList = new ArrayList<>();

		try {
			Reservation rToEdit = rS.findReservationById(rId);
			
			//Getting reservations with same date as current reservation
			for(var r : rS.getAllReservation()) {
				
				if(r.getDate().equals(rToEdit.getDate()) && !r.equals(rToEdit)) {
					resDateList.add(r);
				}
			}
			
			ReservationTimes currentPeriod = rTS.findReservationTByTime(rToEdit.getTime());

			//Adding Tables - Too much code for now (will try to reduce later)
			RTable t10 = tS.findRTableByNumber((long)10);
			int t10ResToday = 0;
			RTable t11 = tS.findRTableByNumber((long)11);
			int t11ResToday = 0;
			RTable t12 = tS.findRTableByNumber((long)12);
			int t12ResToday = 0;
			RTable t40 = tS.findRTableByNumber((long)40);
			int t40ResToday = 0;
			RTable t41 = tS.findRTableByNumber((long)41);
			int t41ResToday = 0;
			RTable t42 = tS.findRTableByNumber((long)42);
			int t42ResToday = 0;
			RTable t43 = tS.findRTableByNumber((long)43);
			int t43ResToday = 0;
			RTable t20 = tS.findRTableByNumber((long)20);
			int t20ResToday = 0;
			RTable t21 = tS.findRTableByNumber((long)21);
			int t21ResToday = 0;
			RTable t22 = tS.findRTableByNumber((long)22);
			int t22ResToday = 0;
			RTable t30 = tS.findRTableByNumber((long)30);
			int t30ResToday = 0;
			RTable t50 = tS.findRTableByNumber((long)50);
			int t50ResToday = 0;
			RTable t51 = tS.findRTableByNumber((long)51);
			int t51ResToday = 0;
			RTable t52 = tS.findRTableByNumber((long)52);
			int t52ResToday = 0;
			
			for (var tR : t10.getReservations()) {
				if(tR.getDate().equals(rToEdit.getDate()) 
						&& isSameResPeriod(tR.getTime(), currentPeriod)) {
					
					t10ResToday = 1;
				}
			}
			for (var tR : t11.getReservations()) {
				if(tR.getDate().equals(rToEdit.getDate()) && isSameResPeriod(tR.getTime(), currentPeriod)) {
					t11ResToday = 1;
				}
			}
			for (var tR : t12.getReservations()) {
				if(tR.getDate().equals(rToEdit.getDate()) && isSameResPeriod(tR.getTime(), currentPeriod)) {
					t12ResToday = 1;
				}
			}
			for (var tR : t40.getReservations()) {
				if(tR.getDate().equals(rToEdit.getDate()) && isSameResPeriod(tR.getTime(), currentPeriod)) {
					t40ResToday = 1;
				}
			}
			for (var tR : t41.getReservations()) {
				if(tR.getDate().equals(rToEdit.getDate()) && isSameResPeriod(tR.getTime(), currentPeriod)) {
					t41ResToday = 1;
				}
			}
			for (var tR : t42.getReservations()) {
				if(tR.getDate().equals(rToEdit.getDate()) && isSameResPeriod(tR.getTime(), currentPeriod)) {
					t42ResToday = 1;
				}
			}
			for (var tR : t43.getReservations()) {
				if(tR.getDate().equals(rToEdit.getDate()) && isSameResPeriod(tR.getTime(), currentPeriod)) {
					t43ResToday = 1;
				}
			}
			for (var tR : t20.getReservations()) {
				if(tR.getDate().equals(rToEdit.getDate()) && isSameResPeriod(tR.getTime(), currentPeriod)) {
					t20ResToday = 1;
				}
			}
			for (var tR : t21.getReservations()) {
				if(tR.getDate().equals(rToEdit.getDate()) && isSameResPeriod(tR.getTime(), currentPeriod)) {
					t21ResToday = 1;
				}
			}
			for (var tR : t22.getReservations()) {
				if(tR.getDate().equals(rToEdit.getDate()) && isSameResPeriod(tR.getTime(), currentPeriod)) {
					t22ResToday = 1;
				}
			}
			for (var tR : t30.getReservations()) {
				if(tR.getDate().equals(rToEdit.getDate()) && isSameResPeriod(tR.getTime(), currentPeriod)) {
					t30ResToday = 1;
				}
			}
			for (var tR : t50.getReservations()) {
				if(tR.getDate().equals(rToEdit.getDate()) && isSameResPeriod(tR.getTime(), currentPeriod)) {
					t50ResToday = 1;
				}
			}
			for (var tR : t51.getReservations()) {
				if(tR.getDate().equals(rToEdit.getDate()) && isSameResPeriod(tR.getTime(), currentPeriod)) {
					t51ResToday = 1;
				}
			}
			for (var tR : t52.getReservations()) {
				if(tR.getDate().equals(rToEdit.getDate()) && isSameResPeriod(tR.getTime(), currentPeriod)) {
					t52ResToday = 1;
				}
			}
			
			model.addAttribute("statusList", ReservationStatus.values());
			model.addAttribute("rToEdit", rToEdit);			
			model.addAttribute("resDateList", resDateList);
			model.addAttribute("currentPeriod", currentPeriod);
			
			model.addAttribute("t10ResToday", t10ResToday);
			model.addAttribute("t11ResToday", t11ResToday);
			model.addAttribute("t12ResToday", t12ResToday);
			model.addAttribute("t40ResToday", t40ResToday);
			model.addAttribute("t41ResToday", t41ResToday);
			model.addAttribute("t42ResToday", t42ResToday);
			model.addAttribute("t43ResToday", t43ResToday);
			model.addAttribute("t20ResToday", t20ResToday);
			model.addAttribute("t21ResToday", t21ResToday);
			model.addAttribute("t22ResToday", t22ResToday);
			model.addAttribute("t30ResToday", t30ResToday);
			model.addAttribute("t50ResToday", t50ResToday);
			model.addAttribute("t51ResToday", t51ResToday);
			model.addAttribute("t52ResToday", t52ResToday);

			return "reservation-process";
		} catch (Exception e) {

			return null;
		}
	}

	@PostMapping("/updateReservation")
	public String processUpdateRes(Reservation rToEdit) {

		rS.updateReservation(rToEdit);

		return "redirect:/toReservationAdmin";
	}

	@PostMapping("/addNewReservation")
	public String toAddReservation(Model model, @RequestParam("reservationTime") String resTime) {

		Reservation reservationToAdd = new Reservation();

		// Getting the current day + 1
		LocalDate currentDatePlusOne = LocalDate.now().plusDays(1);

		reservationToAdd.setTime(resTime);
		model.addAttribute("reservationToAdd", reservationToAdd);
		model.addAttribute("minDate", currentDatePlusOne);

		return "new-reservation";
	}
	
	@PostMapping("/addReservationTimes")
	public String toAddReservationTimes(ReservationTimes rT) {
		
		rTS.addNewReservation(rT);
		
		return "Reservation Time for " + rT.getTime() + " successfully added.";
	}

	@PostMapping("processAddReservation")
	public String processAddReservation(Model model, @ModelAttribute("reservationToAdd") Reservation reservationToAdd, RedirectAttributes ra) {

    	System.out.println("test");
		// Setting status to pending
		reservationToAdd.setStatus(ReservationStatus.PENDING);

		// Adding to database
		Reservation addedReservation = rS.addNewReservation(reservationToAdd);

		// Formatting the reservation date
		String pattern = "dd MMMM yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String formatDate = simpleDateFormat.format(addedReservation.getDate());

		// Email variables
		subject = "New Pending Reservation No. " + addedReservation.getId();
		message = "A new reservation was placed under the name of ... for " + formatDate + " at "
				+ addedReservation.getTime()
				+ ". \n\nGo to /DinningRoom123.ca/pendedReservations to analyze this request.";

		// Sending email
		sendEmail(subject, message, email);

		model.addAttribute("addedReservation", addedReservation);

		return "reservation-confirmation";
	}

	@GetMapping("deleteReservation/{rId}")
	public String deleteReservation(@PathVariable("rId") long rMID, Model model) {

		try {
			rS.deleteReservation(rMID);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/toReservationAdmin";
	}

	private void sendEmail(String subject, String message, String email) {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);

		msg.setSubject(subject);
		msg.setText(message);

		javaMailSender.send(msg);

	}
	
	private boolean isSameResPeriod(String time, ReservationTimes currentPeriod) throws Exception {
		
		ReservationTimes newPeriod = rTS.findReservationTByTime(time);
		
		if(currentPeriod.getResGroup().equals(newPeriod.getResGroup()))
			return true;
		
		return false;
	}
}
