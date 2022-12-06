package ca.nbcc.restapp.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.nbcc.restapp.model.Reservation;

public interface ReservationJpaRepo extends JpaRepository<Reservation, Long>{
	
	String customQueryPastRes = "select r from Reservation r where r.date < CURRENT_DATE-1 order by r.date";
	String customQueryPastResDesc = "select r from Reservation r where r.date < CURRENT_DATE-1 order by r.date desc";
	String customQueryFutureRes = "select r from Reservation r where r.date > CURRENT_DATE-1 order by r.date";
	String customQueryTodayRes = "select r from Reservation r where r.date > CURRENT_DATE-1 AND r.date < CURRENT_DATE  order by r.date";
	String customQueryFutureResDesc = "select r from Reservation r where r.date > CURRENT_DATE-1 order by r.date desc";

	List<Reservation> findByOrderByDateAscTimeDesc();
	
	@Query(customQueryFutureRes)
	List<Reservation> findByOrderByDate();
	
	@Query(customQueryFutureResDesc)
	List<Reservation> findByOrderByDateDesc();
	
	@Query(customQueryTodayRes)
	List<Reservation> findTodayRes();
	
	@Query(customQueryPastRes)
	List<Reservation> findPastReservationsDate();
	
	@Query(customQueryPastResDesc)
	List<Reservation> findPastReservationsDateDesc();
}
