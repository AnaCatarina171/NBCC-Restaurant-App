package ca.nbcc.restapp.model;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	
	@Column(name="TABLE_GUEST_NUMBER")
	private int guestNumber;
	
	@Column(name="TABLE_IS_ROUND")
	private int isRound;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="FK_SECTION_ID")
	private Section section;

	public RTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RTable(int guestNumber, int isRound, Section section) {
		super();
		this.guestNumber = guestNumber;
		this.isRound = isRound;
		this.section = section;
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

	public int getIsRound() {
		return isRound;
	}

	public void setIsRound(int isRound) {
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
		return "RTable [id=" + id + ", guestNumber=" + guestNumber + ", isRound=" + isRound + ", section=" + section
				+ "]";
	}
}
