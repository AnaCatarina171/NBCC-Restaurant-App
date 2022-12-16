package ca.nbcc.restapp.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
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
	
	public List<Reservation> getAllCurrentOrFutureReservation(){
		return rR.findByOrderByDate();
	}
	
	public List<Reservation> getAllReservation(){
		return rR.findAll();
	}
	
	public List<Reservation> getByDate(Date d){
		//return rR.findAll();
		return rR.findByDate(d);
	}
	
	public List<Reservation> orderByDate(){
		return rR.findByOrderByDate();
	}
	
	public List<Reservation> orderByDateDesc(){
		return rR.findByOrderByDateDesc();
	}
	
	public List<Reservation> pastReservations(){
		return rR.findPastReservationsDate();
	}
	
	public List<Reservation> pastReservationsDesc(){
		return rR.findPastReservationsDateDesc();
	}
	
	public List<Reservation> getTodayRes(){
		return rR.findTodayRes();
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
	
	public Page<Reservation> findPaginated(Pageable pageable, List<Reservation> resList) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Reservation> list;

        if (resList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, resList.size());
            list = resList.subList(startItem, toIndex);
        }

        Page<Reservation> resPage
          = new PageImpl<Reservation>(list, PageRequest.of(currentPage, pageSize), resList.size());

        return resPage;
    }
}
