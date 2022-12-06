package ca.nbcc.restapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.nbcc.restapp.model.ReservationTimes;
import ca.nbcc.restapp.repo.ReservationTimeJpaRepo;

@Service
public class ReservationTimeService {

	private ReservationTimeJpaRepo rR;
	
	@Autowired
	public ReservationTimeService(ReservationTimeJpaRepo rR) {
		super();
		this.rR = rR;
	}
	
	public ReservationTimes findReservationById(Long rMID_LONG) throws Exception{
		
		if(rR.findById((long)rMID_LONG).isPresent()) {
			
			return rR.findById((long)rMID_LONG).get();
		}
		else if(rR.findById((long)rMID_LONG).isEmpty()) {
			
			throw new Exception("Reservation Time not found: ID " + rMID_LONG);
		}
		
		return null;
	}
	
	public ReservationTimes findReservationTByTime(String rMID_LONG) throws Exception{
		
		if(rR.findByTime(rMID_LONG) != null) {
			
			return rR.findByTime(rMID_LONG);
		}
		else if(rR.findByTime(rMID_LONG) == null) {
			
			throw new Exception("Reservation Time not found: ID " + rMID_LONG);
		}
		
		return null;
	}
	
	public List<ReservationTimes> getAllReservationTimes(){
		return rR.findAll();
	}
	
	public ReservationTimes addNewReservation(ReservationTimes r) {
		return rR.save(r);
	}

	public ReservationTimes updateReservation(ReservationTimes r) {
		return rR.save(r);
	}

	public void deleteReservation(Long rMID_LONG) {
			
		rR.deleteById(rMID_LONG);
	}
}
