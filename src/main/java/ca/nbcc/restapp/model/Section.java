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
@Table(name="SECTION_Table")
public class Section {

	@Id
	@SequenceGenerator(name = "SECTION_SEQ_GEN", sequenceName = "SECTION_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SECTION_SEQ_GEN")
	@Column(name = "SECTION_ID", unique = true)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="FK_FLOOR_PLAN_ID")
	private FloorPlan floorPlan;

	public Section() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Section(FloorPlan floorPlan) {
		super();
		this.floorPlan = floorPlan;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FloorPlan getFloorPlan() {
		return floorPlan;
	}

	public void setFloorPlan(FloorPlan floorPlan) {
		this.floorPlan = floorPlan;
	}

	@Override
	public int hashCode() {
		return Objects.hash(floorPlan, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Section other = (Section) obj;
		return Objects.equals(floorPlan, other.floorPlan) && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Section [id=" + id + ", floorPlan=" + floorPlan + "]";
	}
	
	
}
