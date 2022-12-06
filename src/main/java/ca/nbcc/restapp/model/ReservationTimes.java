package ca.nbcc.restapp.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Reservation_Times_Table")
public class ReservationTimes {

	@Id
	@SequenceGenerator(name = "RES__TIME_SEQ_GEN", sequenceName = "RES__TIME_SEQ", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RES_TIME_SEQ_GEN")
	@Column(name = "RES_TIME_ID", unique = true)
	private Long id;
	
	@Column(name="RES_T_TIME", unique=true)
	private String time;
	
	@Column(name="RES_T_GROUP")
	private ReservationTimeGroup resGroup;

	public ReservationTimes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReservationTimes(String time, ReservationTimeGroup resGroup) {
		super();
		this.time = time;
		this.resGroup = resGroup;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public ReservationTimeGroup getResGroup() {
		return resGroup;
	}

	public void setResGroup(ReservationTimeGroup resGroup) {
		this.resGroup = resGroup;
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hash(id, resGroup, time);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReservationTimes other = (ReservationTimes) obj;
		return Objects.equals(id, other.id) && resGroup == other.resGroup && Objects.equals(time, other.time);
	}

	@Override
	public String toString() {
		return "ReservationTimes [id=" + id + ", time=" + time + ", resGroup=" + resGroup + "]";
	}
	
	
}
