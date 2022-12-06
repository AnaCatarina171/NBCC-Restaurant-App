package ca.nbcc.restapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.nbcc.restapp.model.ReservationTimes;

public interface ReservationTimeJpaRepo extends JpaRepository<ReservationTimes, Long> {

	ReservationTimes findByTime(String time);
}
