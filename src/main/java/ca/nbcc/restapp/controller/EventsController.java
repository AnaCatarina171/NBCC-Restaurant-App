package ca.nbcc.restapp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.nbcc.restapp.model.Event;
import ca.nbcc.restapp.service.EventService;

@Controller
public class EventsController {
	
	private EventService eS;
	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/uploads";
	
	@Autowired
	public EventsController(EventService eS) {
		super();
		this.eS = eS;
	}
	
	@GetMapping("/eventsPage")
	public String eventsPage(Model model) {

		List<Event> allEvents = eS.getAllEvent();
		List<Event> displayedEvents = new ArrayList<>();
		
		for(var a : allEvents) {
			
			if(a.isDisplayed()) {
				displayedEvents.add(a);
			}
		}
		
		model.addAttribute("allEvents", allEvents);
		model.addAttribute("displayedEvents", displayedEvents);
		model.addAttribute("UPLOAD_DIRECTORY", UPLOAD_DIRECTORY);
		
		return "user-events";
	}
	
	@GetMapping("/guestsEventsPage")
	public String guestsEventsPage(Model model) {

		List<Event> displayedEvents = new ArrayList<>();
		
		for(var a : eS.getAllEvent()) {
			
			if(a.isDisplayed()) {
				displayedEvents.add(a);
			}
		}
		
		model.addAttribute("displayedEvents", displayedEvents);
		
		return "new-events";
	}
	
	@GetMapping("/displayEvent/{eId}")
	public String displayEvent(Model model) {

		List<Event> allEvents = eS.getAllEvent();
		
		model.addAttribute("allEvents", allEvents);
		
		return "user-events";
	}

	@PostMapping("/uploadEventImage") 
	public String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {
		
		StringBuilder fileNames = new StringBuilder();
		Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
		fileNames.append(file.getOriginalFilename());
		Files.write(fileNameAndPath, file.getBytes());
		model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
		
		System.out.println(fileNames);
		
		return "user-new-events";
	}
	
	@GetMapping("/addNewEvent")
	public String toAddEvent(Model model) {

		Event eventToAdd = new Event();

		model.addAttribute("eventToAdd", eventToAdd);

		return "user-new-events";
	}

	@PostMapping("processAddEvent")
	public String processAddEvent(Model model, Event eventToAdd,
			@RequestParam("image") MultipartFile file, @RequestParam("isDisplayed") boolean isDisplayed) throws IOException {

		//Adding image to uploads folders
		StringBuilder fileNames = new StringBuilder();
		Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
		fileNames.append(file.getOriginalFilename());
		Files.write(fileNameAndPath, file.getBytes());
		model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
		
		try {
			//Setting imageUrl to current uploaded image
			eventToAdd.setImageUrl(fileNames.toString());
			
			//Setting isDisplayed variable
			eventToAdd.setDisplayed(isDisplayed);
			
			// Adding to database
			Event addedEvent = eS.addNewEvent(eventToAdd);
			
			//model.addAttribute("addedEvent", addedEvent);

			return "redirect:/eventsPage";
			
		} catch (Exception ex) {
			return "user-new-events";
		}

	}
	
	@GetMapping("/toEditEvent/{eId}")
	public String toEditEvent(Model model, @PathVariable("eId") long eMID) throws Exception {

		Event eventToEdit = eS.findEventById(eMID);

		model.addAttribute("eventToEdit", eventToEdit);

		return "edit-event";
	}
	
	@PostMapping("processEditEvent")
	public String processEditEvent(Model model, Event eventToEdit,
			@RequestParam("image") MultipartFile file, @RequestParam("isDisplayed") boolean isDisplayed) throws IOException {

		//Adding image to uploads folders
		
		if(!file.isEmpty()) {
			StringBuilder fileNames = new StringBuilder();
			Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
			fileNames.append(file.getOriginalFilename());
			Files.write(fileNameAndPath, file.getBytes());
			model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
			
			//Setting imageUrl to current uploaded image
			eventToEdit.setImageUrl(fileNames.toString());
		}
		
		try {
			
			//Setting isDisplayed variable
			eventToEdit.setDisplayed(isDisplayed);
			
			// Adding to database
			Event editEvent = eS.updateEvent(eventToEdit);
			
			//model.addAttribute("addedEvent", addedEvent);

			return "redirect:/eventsPage";
			
		} catch (Exception ex) {
			return "user-new-events";
		}

	}

	@GetMapping("deleteEvent/{eId}")
	public String deleteEvent(@PathVariable("eId") long eMID, Model model) {

		try {
			eS.deleteEvent(eMID);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/eventsPage";
	}

}
