package ca.nbcc.restapp.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.nbcc.restapp.model.Reservation;

public interface ReservationJpaRepo extends JpaRepository<Reservation, Long>{
	
	String customQuery = "select r from Reservation r where r.date < CURRENT_DATE";
	
	List<Reservation> findByOrderByDateAscTimeDesc();
	
	List<Reservation> findByOrderByDate();
	
	List<Reservation> findByOrderByDateDesc();
	
	@Query(customQuery)
	List<Reservation> findPastReservations();
}
