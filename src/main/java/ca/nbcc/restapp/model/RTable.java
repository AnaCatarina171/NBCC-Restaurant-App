package ca.nbcc.restapp.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="RTable_Table")
public class RTable {

	@Id
	@SequenceGenerator(name = "TABLE_SEQ_GEN", sequenceName = "TABLE_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TABLE_SEQ_GEN")
	@Column(name = "TABLE_ID", unique = true)
	private Long id;
	
	@Column(name="TABLE_NUMBER", unique=true)
	private Long number;
	
	@Column(name="TABLE_GUEST_NUMBER")
	private int guestNumber;
	
	@Column(name="TABLE_IS_ROUND")
	private boolean isRound;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="FK_SECTION_ID")
	private Section section;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="table")
	private List<Reservation> reservations;

	public RTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RTable(Long number, int guestNumber, boolean isRound, Section section) {
		super();
		this.number = number;
		this.guestNumber = guestNumber;
		this.isRound = isRound;
		this.section = section;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getGuestNumber() {
		return guestNumber;
	}

	public void setGuestNumber(int guestNumber) {
		this.guestNumber = guestNumber;
	}

	public boolean getIsRound() {
		return isRound;
	}

	public void setIsRound(boolean isRound) {
		this.isRound = isRound;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	@Override
	public int hashCode() {
		return Objects.hash(guestNumber, id, isRound, section);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RTable other = (RTable) obj;
		return guestNumber == other.guestNumber && Objects.equals(id, other.id) && isRound == other.isRound
				&& Objects.equals(section, other.section);
	}

	@Override
	public String toString() {
		return "RTable [id=" + id + ", number=" + number + ", guestNumber=" + guestNumber + ", isRound=" + isRound + ", section=" + section
				+ "]";
	}
}
