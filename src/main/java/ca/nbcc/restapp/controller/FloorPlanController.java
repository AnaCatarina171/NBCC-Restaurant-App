package ca.nbcc.restapp.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ca.nbcc.restapp.model.RTable;
import ca.nbcc.restapp.model.Reservation;
import ca.nbcc.restapp.model.ReservationStatus;
import ca.nbcc.restapp.service.RTableService;
import ca.nbcc.restapp.service.ReservationService;

@Controller
public class FloorPlanController {

	ApplicationContext ctx;

	ReservationService rS;
	private RTableService tS;

	public FloorPlanController(ApplicationContext ctx, ReservationService rS, RTableService tS) {
		super();
		this.ctx = ctx;
		this.rS = rS;
		this.tS = tS;
	}

	@GetMapping("/toFloorPlanAdmin")
	public String goToFloorPlan(Model model) {

		LocalDate today = LocalDate.now();

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
				if (tR.getDate().equals(today)) {
					t10ResToday = 1;
				}
			}
			for (var tR : t11.getReservations()) {
				if (tR.getDate().equals(today)) {
					t11ResToday = 1;
				}
			}
			for (var tR : t12.getReservations()) {
				if (tR.getDate().equals(today)) {
					t12ResToday = 1;
				}
			}
			for (var tR : t40.getReservations()) {
				if (tR.getDate().equals(today)) {
					t40ResToday = 1;
				}
			}
			for (var tR : t41.getReservations()) {
				if (tR.getDate().equals(today)) {
					t41ResToday = 1;
				}
			}
			for (var tR : t42.getReservations()) {
				if (tR.getDate().equals(today)) {
					t42ResToday = 1;
				}
			}
			for (var tR : t43.getReservations()) {
				if (tR.getDate().equals(today)) {
					t43ResToday = 1;
				}
			}
			for (var tR : t20.getReservations()) {
				if (tR.getDate().equals(today)) {
					t20ResToday = 1;
				}
			}
			for (var tR : t21.getReservations()) {
				if (tR.getDate().equals(today)) {
					t21ResToday = 1;
				}
			}
			for (var tR : t22.getReservations()) {
				if (tR.getDate().equals(today)) {
					t22ResToday = 1;
				}
			}
			for (var tR : t30.getReservations()) {
				if (tR.getDate().equals(today)) {
					t30ResToday = 1;
				}
			}
			for (var tR : t50.getReservations()) {
				if (tR.getDate().equals(today)) {
					t50ResToday = 1;
				}
			}
			for (var tR : t51.getReservations()) {
				if (tR.getDate().equals(today)) {
					t51ResToday = 1;
				}
			}
			for (var tR : t52.getReservations()) {
				if (tR.getDate().equals(today)) {
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

			return "floor-plan";
		} catch (Exception e) {

			return null;
		}
	}
}
