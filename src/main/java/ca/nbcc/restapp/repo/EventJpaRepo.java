package ca.nbcc.restapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.nbcc.restapp.model.Event;
import ca.nbcc.restapp.model.Reservation;

public interface EventJpaRepo extends JpaRepository<Event, Long>{

	String customQueryDisplayedEvents = "select e from Event e where e.isDisplayed = TRUE";
	
	@Query(customQueryDisplayedEvents)
	List<Event> findDisplayedEvents();
}
