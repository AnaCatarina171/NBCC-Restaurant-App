package ca.nbcc.restapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.nbcc.restapp.model.Reservation;
import ca.nbcc.restapp.repo.ReservationJpaRepo;

@Service
public class ReservationService {

	private ReservationJpaRepo rR;
	
	@Autowired
	public ReservationService(ReservationJpaRepo rR) {
		super();
		this.rR = rR;
	}
	
	/*public Reservation findReservationByUsername(String username) {
		
		return er.findByusername(username);
	}*/
	
	public Reservation findReservationById(Long rMID_LONG) throws Exception{
		
		if(rR.findById((long)rMID_LONG).isPresent()) {
			
			return rR.findById((long)rMID_LONG).get();
		}
		else if(rR.findById((long)rMID_LONG).isEmpty()) {
			
			throw new Exception("Reservation not found: ID " + rMID_LONG);
		}
		
		return null;
	}
	
	
	public List<Reservation> getAllReservation(){
		return rR.findAll();
	}
	
	public Reservation addNewReservation(Reservation r) {
		return rR.save(r);
	}

	public Reservation updateReservation(Reservation r) {
		return rR.save(r);
	}

	public void deleteReservation(Long rMID_LONG) {
			
		rR.deleteById(rMID_LONG);
	}
}
