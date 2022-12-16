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
@Table(name="Event_Table")
public class Event {

	@Id
	@SequenceGenerator(name = "EVENT_SEQ_GEN", sequenceName = "EVENT_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVENT_SEQ_GEN")
	@Column(name = "EVENT_ID", unique = true)
	private Long id;
	
	@Column(name="EVENT_DESCRIPTION")
	private String description;
	
	@Column(name="EVENT_IMAGE_URL")
	private String imageUrl;

	public Event() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Event(String description, String imageUrl) {
		super();
		this.description = description;
		this.imageUrl = imageUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, imageUrl);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(imageUrl, other.imageUrl);
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", description=" + description + ", imageUrl=" + imageUrl + "]";
	}
}
