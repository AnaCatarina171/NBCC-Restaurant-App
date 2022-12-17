package ca.nbcc.restapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.nbcc.restapp.model.Event;
import ca.nbcc.restapp.repo.EventJpaRepo;

@Service
public class EventService {

	private EventJpaRepo er;
	
	@Autowired
	public EventService(EventJpaRepo er) {
		super();
		this.er = er;
	}
	
	public Event findEventById(Long eMID_LONG) throws Exception{
		if(er.findById((long)eMID_LONG).isPresent()) {
			return er.findById((long)eMID_LONG).get();
		}
		else if(er.findById((long)eMID_LONG).isEmpty()) {
			throw new Exception("Event not found: ID " + eMID_LONG);
		}
		return null;
	}
	
	public List<Event> getAllEvent(){
		return er.findAll();
	}
	
	public List<Event> getDisplayedEvents(){
		return er.findDisplayedEvents();
	}
	
	public Event addNewEvent(Event e) {
		return er.save(e);
	}

	public Event updateEvent(Event e) {
		return er.save(e);
	}

	public void deleteEvent(Long eMID_LONG) {
			
		er.deleteById(eMID_LONG);
	}
}
