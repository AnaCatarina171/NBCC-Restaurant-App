package ca.nbcc.restapp.controller;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	private String email = "anaaccatarina@gmail.com"; // MO.Hospitality@nbcc.ca
	private String subject;
	private String message;

	public ReservationController() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReservationController(ApplicationContext ctx, ReservationService rS, CustomerService cS,
			ReservationTimeService rTS) {
		super();
		this.ctx = ctx;
		this.rS = rS;
		this.cS = cS;
		this.rTS = rTS;
	}

	@Autowired
	public ReservationController(ReservationService rS, ReservationTimeService rTS, RTableService tS) {
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

		for (var resT : rTS.getAllReservationTimes()) {

			if (resT.getResGroup().equals(ReservationTimeGroup.BREAKFAST)) {
				resTimeBreakfast.add(resT);
			} else if (resT.getResGroup().equals(ReservationTimeGroup.LUNCH)) {
				resTimeLunch.add(resT);
			} else if (resT.getResGroup().equals(ReservationTimeGroup.NIGHT)) {
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
	public String todayResFloorPlan(Model model, @RequestParam(value = "date", required = false) String date,
			@RequestParam(value = "currentPeriod", required = false) String currentPeriod,
			@RequestParam("bPage") Optional<Integer> bPage, @RequestParam("bSize") Optional<Integer> bSize,
			@RequestParam("lPage") Optional<Integer> lPage, @RequestParam("lSize") Optional<Integer> lSize,
			@RequestParam("nPage") Optional<Integer> nPage, @RequestParam("nSize") Optional<Integer> nSize)
			throws Exception {

		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdt2 = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		String selectedDateS = null;

		Date selectedDate = new Date();

		List<Reservation> todayReservations = rS.getTodayRes();

		List<Reservation> breakfastReservations = new ArrayList<>();
		List<Reservation> lunchReservations = new ArrayList<>();
		List<Reservation> nightReservations = new ArrayList<>();

		ReservationTimeGroup currentPeriodR = null;

		if (date != null && date != "") {
			if (date.contains("AST")) {
				selectedDate = sdt2.parse(date);
			} else {
				selectedDate = sdt.parse(date);
			}

			todayReservations = rS.getByDate(selectedDate);
		}

		selectedDateS = sdt.format(selectedDate);

		for (var r : todayReservations) {
    
			// Getting only confirmed reservations
			if (r.getStatus().equals(ReservationStatus.CONFIRMED)) {

				ReservationTimes newPeriod = rTS.findReservationTByTime(r.getTime());

				if (newPeriod.getResGroup().equals(ReservationTimeGroup.BREAKFAST)) {
					breakfastReservations.add(r);
				} else if (newPeriod.getResGroup().equals(ReservationTimeGroup.LUNCH)) {
					lunchReservations.add(r);
				} else if (newPeriod.getResGroup().equals(ReservationTimeGroup.NIGHT)) {
					nightReservations.add(r);
				}
			}
		}

		if (currentPeriod != null) {
			if (!currentPeriod.equals("0"))
				currentPeriodR = ReservationTimeGroup.valueOf(currentPeriod);
		}

		// Breakfast Res Pagination
		int bCurrentPage = bPage.orElse(1);
		int bPageSize = bSize.orElse(6);

		Page<Reservation> bResPage = rS.findPaginated(PageRequest.of(bCurrentPage - 1, bPageSize), breakfastReservations);

		model.addAttribute("bResPage", bResPage);

		int bTotalPages = bResPage.getTotalPages();
		if (bTotalPages > 0) {
			List<Integer> bPageNumbers = IntStream.rangeClosed(1, bTotalPages).boxed().collect(Collectors.toList());
			model.addAttribute("bPageNumbers", bPageNumbers);
		}
		//

		// Lunch Res Pagination
		int lCurrentPage = lPage.orElse(1);
		int lPageSize = lSize.orElse(6);

		Page<Reservation> lResPage = rS.findPaginated(PageRequest.of(lCurrentPage - 1, lPageSize), lunchReservations);

		model.addAttribute("lResPage", lResPage);

		int lTotalPages = lResPage.getTotalPages();
		if (lTotalPages > 0) {
			List<Integer> lPageNumbers = IntStream.rangeClosed(1, lTotalPages).boxed().collect(Collectors.toList());
			model.addAttribute("lPageNumbers", lPageNumbers);
		}
		//

		// Night Res Pagination
		int nCurrentPage = nPage.orElse(1);
		int nPageSize = nSize.orElse(6);

		Page<Reservation> nResPage = rS.findPaginated(PageRequest.of(nCurrentPage - 1, nPageSize), nightReservations);

		model.addAttribute("nResPage", nResPage);

		int nTotalPages = nResPage.getTotalPages();
		if (nTotalPages > 0) {
			List<Integer> nPageNumbers = IntStream.rangeClosed(1, nTotalPages).boxed().collect(Collectors.toList());
			model.addAttribute("nPageNumbers", nPageNumbers);
		}
		//

		try {

			findingTablesReservations(model, sdt, selectedDateS, currentPeriodR);

			model.addAttribute("breakfastReservations", breakfastReservations);
			model.addAttribute("lunchReservations", lunchReservations);
			model.addAttribute("nightReservations", nightReservations);

			model.addAttribute("selectedDate", selectedDate);

			model.addAttribute("allPeriods", ReservationTimeGroup.values());
			model.addAttribute("currentPeriod", currentPeriod);

			return "reservation-floor-plan";
		} catch (Exception e) {

			return null;
		}
	}

	@GetMapping("/viewReservations")
	public String goToReservations(Model model, String rId, String pastRId,
			@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
			@RequestParam("pPage") Optional<Integer> pPage, @RequestParam("pSize") Optional<Integer> pSize,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "orderByP", required = false) String orderByP,
			@RequestParam(value = "status", required = false) String status) throws Exception {

		try {

			List<Reservation> allReservations = rS.getAllCurrentOrFutureReservation();
			List<Reservation> pastReservations = rS.pastReservations();

			if (rId != null && rId != "" && !rId.isEmpty()) {
				Long rLongId = Long.parseLong(rId);

				allReservations = new ArrayList<>();

				try {
					allReservations.add(rS.findReservationById(rLongId));
				} catch (Exception ex) {
					allReservations = rS.getAllCurrentOrFutureReservation();
				}
			}

			if (pastRId != null && pastRId != "" && !pastRId.isEmpty()) {
				Long rLongId = Long.parseLong(pastRId);

				pastReservations = new ArrayList<>();

				try {
					pastReservations.add(rS.findReservationById(rLongId));
				} catch (Exception ex) {
					pastReservations = rS.pastReservations();
				}
			}

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
			if (status != null && !status.equals("0")) {
				ReservationStatus rStatus = ReservationStatus.valueOf(status);

				for (var r : rS.getAllCurrentOrFutureReservation()) {
					if (!r.getStatus().equals(rStatus)) {
						allReservations.remove(r);
					}
				}
			}

			// Current-Future Res Pagination
			int currentPage = page.orElse(1);
			int pageSize = size.orElse(12);

			Page<Reservation> resPage = rS.findPaginated(PageRequest.of(currentPage - 1, pageSize), allReservations);

			model.addAttribute("resPage", resPage);

			int totalPages = resPage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			//

			// Past Res Pagination
			int pCurrentPage = pPage.orElse(1);
			int pPageSize = pSize.orElse(12);

			Page<Reservation> pResPage = rS.findPaginated(PageRequest.of(pCurrentPage - 1, pPageSize),
					pastReservations);

			model.addAttribute("pResPage", pResPage);

			int pTotalPages = pResPage.getTotalPages();
			if (pTotalPages > 0) {
				List<Integer> pPageNumbers = IntStream.rangeClosed(1, pTotalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pPageNumbers", pPageNumbers);
			}
			//

			model.addAttribute("orderBySelected", orderBy);
			model.addAttribute("orderBySelectedP", orderByP);
			model.addAttribute("pastReservations", pastReservations);
			model.addAttribute("reservations", allReservations);
			model.addAttribute("statusList", ReservationStatus.values());

			return "reservations-display";
		} catch (Exception ex) {

			return "reservations-display";
		}

	}

	@GetMapping("toReservationAdmin")
	public String toReservationPanel(Model model) {

		List<Reservation> allReservations = rS.getAllCurrentOrFutureReservation();
		List<Reservation> pendingReservations = new ArrayList<>();
		List<Reservation> weekReservations = new ArrayList<>();

		for (var res : allReservations) {

			if (res.getStatus().equals(ReservationStatus.PENDING)) {
				pendingReservations.add(res);
			}
			if (res.getStatus().equals(ReservationStatus.CONFIRMED)) {

				Date resDate = res.getDate();
				LocalDate resLocalDate = resDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

				LocalDate today = LocalDate.now();

				LocalDate monday = today;

				do {
					monday = monday.plusDays(1);
				} while (monday.getDayOfWeek() != DayOfWeek.MONDAY);

				if ((resLocalDate.isEqual(today) || (resLocalDate.isAfter(today)) && resLocalDate.isBefore(monday))) {
					weekReservations.add(res);
				}
			}
		}

		model.addAttribute("pendingReservations", pendingReservations);
		model.addAttribute("weekReservations", weekReservations);
		model.addAttribute("allReservations", allReservations);

		return "user-admin-reservations";
	}

	@GetMapping("/addResTable/{rId}/{tId}")
	public String addResTable(Model model, @PathVariable("rId") String rId, @PathVariable("tId") String tId)
			throws Exception {

		Long resNumber = Long.parseLong(rId);
		Long tableNumber = Long.parseLong(tId);
		RTable table = null;

		Reservation reservation = rS.findReservationById(resNumber);

		for (var t : tS.getAllRTable()) {
			if (t.getNumber().equals(tableNumber)) {
				table = t;
			}
		}

		reservation.setTable(table);

		rS.updateReservation(reservation);

		return "redirect:/processReservation/" + rId;
	}

	@GetMapping("/processReservation/{rId}")
	public String processNewReservation(Model model, @PathVariable("rId") long rId,
			@RequestParam(value = "currentPeriod", required = false) String currentPeriod) {

		// Store reservations with same date as current reservation
		List<Reservation> resDateList = new ArrayList<>();

		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
		String currentResDateS = null;

		try {
			Reservation rToEdit = rS.findReservationById(rId);

			currentResDateS = sdt.format(rToEdit.getDate());

			// Getting confirmed reservations with same date as current reservation
			for (var r : rS.getAllReservation()) {

				if (r.getDate().equals(rToEdit.getDate()) && !r.equals(rToEdit)) {

					if (r.getStatus().equals(ReservationStatus.CONFIRMED)) {
						resDateList.add(r);
					}
				}
			}

			ReservationTimeGroup currentPeriodR = rTS.findReservationTByTime(rToEdit.getTime()).getResGroup();

			if (currentPeriod != null) {
				if (!currentPeriod.equals("0"))
					currentPeriodR = ReservationTimeGroup.valueOf(currentPeriod);
			}

			findingTablesReservations(model, sdt, currentResDateS, currentPeriodR);

			model.addAttribute("statusList", ReservationStatus.values());
			model.addAttribute("allPeriods", ReservationTimeGroup.values());
			model.addAttribute("rToEdit", rToEdit);
			model.addAttribute("resDateList", resDateList);
			model.addAttribute("currentPeriodR", currentPeriodR);

			return "reservation-process";
		} catch (Exception e) {

			return null;
		}
	}

	@PostMapping("/updateReservation")
	public String processUpdateRes(Reservation rToEdit) {

		if (!rToEdit.getStatus().equals(ReservationStatus.CONFIRMED)) {
			rToEdit.setTable(null);
		}

		rS.updateReservation(rToEdit);

		// Formatting the reservation date
		String pattern = "dd MMMM yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String formatDate = simpleDateFormat.format(rToEdit.getDate());

		// Sending email to Customer
		/*
		 * if (rToEdit.getStatus().equals(ReservationStatus.CONFIRMED)) {
		 * 
		 * String customerSubject = "Reservation Accepted - Dining Room 1234 "; String
		 * customerMessage = "Your reservation #" + rToEdit.getId() +
		 * " at Dining Room 1234 was confirmed! \n" + "We are waiting to see you on " +
		 * formatDate + " !" +
		 * "\n\n\n\n------------------------------ Reservation Info ------------------------------"
		 * + "\n\n				Reservation #" + rToEdit.getId() +
		 * "\n				Number of Guests: " + rToEdit.getGuests() +
		 * "\n				Date: " + formatDate + " - " + rToEdit.getTime() +
		 * "\n				Customer Name: " + rToEdit.getFirstName() + " " +
		 * rToEdit.getLastName(); String customerEmail = rToEdit.getEmail();
		 * sendEmail(customerSubject, customerMessage, customerEmail);
		 * 
		 * } else if (rToEdit.getStatus().equals(ReservationStatus.DENIED)) {
		 * 
		 * String customerSubject = "Reservation Denied - Dining Room 1234"; String
		 * customerMessage = "Unfortunately, due to the high volume of reservations on "
		 * + formatDate + " , your reservation #" + rToEdit.getId() +
		 * " was denied. \n\n" +
		 * "Try again for another date, or send us an email at: MO.Hospitality@nbcc.ca \n\n"
		 * + "We apologize for the inconvenience, and we hope to see you soon!" +
		 * "\n\n\n\n------------------------------ Reservation Info ------------------------------"
		 * + "\n\n				Reservation #" + rToEdit.getId() +
		 * "\n				Number of Guests: " + rToEdit.getGuests() +
		 * "\n				Date: " + formatDate + " - " + rToEdit.getTime() +
		 * "\n				Customer Name: " + rToEdit.getFirstName() + " " +
		 * rToEdit.getLastName(); String customerEmail = rToEdit.getEmail();
		 * sendEmail(customerSubject, customerMessage, customerEmail); }
		 */

		return "redirect:/processReservation/" + rToEdit.getId();
	}

	@GetMapping("/deleteResTable/{rId}")
	public String deleteResTable(Model model, @PathVariable("rId") long rId) {

		try {
			Reservation currentRes = rS.findReservationById(rId);

			currentRes.setTable(null);

			rS.updateReservation(currentRes);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return "redirect:/processReservation/" + rId;
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

	public void deleteResTimes(Long id) {

		rTS.deleteReservation(id);
	}

	public List<ReservationTimes> getAllReservationTimes() {

		return rTS.getAllReservationTimes();
	}

	@PostMapping("processAddReservation")
	public String processAddReservation(Model model, @ModelAttribute("reservationToAdd") Reservation reservationToAdd,
			RedirectAttributes ra) {

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
				+ ". \n\nGo to /DinningRoom123.ca/viewReservations to analyze this request.";

		// Sending email to Dining Room
		// sendEmail(subject, message, email);

		// Sending email to Customer
		String customerSubject = "New Reservation at Dining Room 1234";
		String customerMessage = "Thanks for placing a new reservation at Dining Room 1234 ! \n"
				+ "A member of our team will analyze your reservation and change its status as soon as possible."
				+ "\n\n\n\n------------------------------ Reservation Info ------------------------------"
				+ "\n\n				Reservation #" + reservationToAdd.getId() + "\n				Number of Guests: "
				+ reservationToAdd.getGuests() + "\n				Date: " + formatDate + " - "
				+ reservationToAdd.getTime() + "\n				Customer Name: " + reservationToAdd.getFirstName() + " "
				+ reservationToAdd.getLastName();
		String customerEmail = reservationToAdd.getEmail();
		// sendEmail(customerSubject, customerMessage, customerEmail);

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
	
	private List<Object> createCustomizedPagination(Optional<Integer> page, Optional<Integer> size, List<Reservation> resList){
		
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(6);
		List<Integer> pageNumbers = new ArrayList<>();

		Page<Reservation> resPage = rS.findPaginated(PageRequest.of(currentPage - 1, pageSize), resList);

		int totalPages = resPage.getTotalPages();
		if (totalPages > 0) {
			pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
		}
		
		return Arrays.asList(resPage, pageNumbers);
	}

	private void sendEmail(String subject, String message, String email) {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);

		msg.setSubject(subject);
		msg.setText(message);

		javaMailSender.send(msg);

	}

	private boolean isSameResPeriod(String time, ReservationTimeGroup currentPeriod) throws Exception {

		ReservationTimes newPeriod = rTS.findReservationTByTime(time);

		if (currentPeriod.equals(newPeriod.getResGroup()))
			return true;

		return false;
	}

	/**
	 * Goes through all the tables on the database, checking whether the table has
	 * reservations for the same date as the selectedDateS param, and, if the
	 * currentPeriodR is different than null, it also checks if those reservations
	 * matches this period (Breakfast, Lunch or Night)
	 * 
	 * @param model
	 * @param sdt
	 * @param selectedDateS
	 * @param currentPeriodR
	 */
	private void findingTablesReservations(Model model, SimpleDateFormat sdt, String selectedDateS,
			ReservationTimeGroup currentPeriodR) {

		try {

			// Adding Tables To Floor Plan - Too much code for now (will try to reduce
			// later)
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
				if (resDate.equals(selectedDateS)) {
					if (currentPeriodR == null) {
						t10ResToday = 1;
					} else if (isSameResPeriod(tR.getTime(), currentPeriodR)) {
						t10ResToday = 1;
					}
				}
			}
			for (var tR : t11.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(selectedDateS)) {
					if (currentPeriodR == null) {
						t11ResToday = 1;
					} else if (isSameResPeriod(tR.getTime(), currentPeriodR)) {
						t11ResToday = 1;
					}
				}
			}
			for (var tR : t12.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(selectedDateS)) {
					if (currentPeriodR == null) {
						t12ResToday = 1;
					} else if (isSameResPeriod(tR.getTime(), currentPeriodR)) {
						t12ResToday = 1;
					}
				}
			}
			for (var tR : t40.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(selectedDateS)) {
					if (currentPeriodR == null) {
						t40ResToday = 1;
					} else if (isSameResPeriod(tR.getTime(), currentPeriodR)) {
						t40ResToday = 1;
					}
				}
			}
			for (var tR : t41.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(selectedDateS)) {
					if (currentPeriodR == null) {
						t41ResToday = 1;
					} else if (isSameResPeriod(tR.getTime(), currentPeriodR)) {
						t41ResToday = 1;
					}
				}
			}
			for (var tR : t42.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(selectedDateS)) {
					if (currentPeriodR == null) {
						t42ResToday = 1;
					} else if (isSameResPeriod(tR.getTime(), currentPeriodR)) {
						t42ResToday = 1;
					}
				}
			}
			for (var tR : t43.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(selectedDateS)) {
					if (currentPeriodR == null) {
						t43ResToday = 1;
					} else if (isSameResPeriod(tR.getTime(), currentPeriodR)) {
						t43ResToday = 1;
					}
				}
			}
			for (var tR : t20.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(selectedDateS)) {
					if (currentPeriodR == null) {
						t20ResToday = 1;
					} else if (isSameResPeriod(tR.getTime(), currentPeriodR)) {
						t20ResToday = 1;
					}
				}
			}
			for (var tR : t21.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(selectedDateS)) {
					if (currentPeriodR == null) {
						t21ResToday = 1;
					} else if (isSameResPeriod(tR.getTime(), currentPeriodR)) {
						t21ResToday = 1;
					}
				}
			}
			for (var tR : t22.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(selectedDateS)) {
					if (currentPeriodR == null) {
						t22ResToday = 1;
					} else if (isSameResPeriod(tR.getTime(), currentPeriodR)) {
						t22ResToday = 1;
					}
				}
			}
			for (var tR : t30.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(selectedDateS)) {
					if (currentPeriodR == null) {
						t30ResToday = 1;
					} else if (isSameResPeriod(tR.getTime(), currentPeriodR)) {
						t30ResToday = 1;
					}
				}
			}
			for (var tR : t50.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(selectedDateS)) {
					if (currentPeriodR == null) {
						t50ResToday = 1;
					} else if (isSameResPeriod(tR.getTime(), currentPeriodR)) {
						t50ResToday = 1;
					}
				}
			}
			for (var tR : t51.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(selectedDateS)) {
					if (currentPeriodR == null) {
						t51ResToday = 1;
					} else if (isSameResPeriod(tR.getTime(), currentPeriodR)) {
						t51ResToday = 1;
					}
				}
			}
			for (var tR : t52.getReservations()) {
				String resDate = sdt.format(tR.getDate());
				if (resDate.equals(selectedDateS)) {
					if (currentPeriodR == null) {
						t52ResToday = 1;
					} else if (isSameResPeriod(tR.getTime(), currentPeriodR)) {
						t52ResToday = 1;
					}
				}
			}

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
		} catch (Exception ex) {

			String errorMessage = ex.getMessage();
		}
	}
}
