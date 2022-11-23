package ca.nbcc.restapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.nbcc.restapp.model.Reservation;

public interface ProductJpaRepo extends JpaRepository<Reservation, Long>{

}
