package ca.nbcc.restapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.nbcc.restapp.model.RTable;
import ca.nbcc.restapp.model.ReservationTimes;

public interface RTableJpaRepo extends JpaRepository<RTable, Long> {

	RTable findByNumber(Long number);
}
