package ca.nbcc.restapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.nbcc.restapp.model.Event;

public interface EventJpaRepo extends JpaRepository<Event, Long>{

}
