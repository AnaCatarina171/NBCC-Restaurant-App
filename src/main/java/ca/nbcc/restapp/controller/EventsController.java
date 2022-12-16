package ca.nbcc.restapp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class EventsController {

	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir" + "/uploads");
	
	@GetMapping("/uploadImage")
	public String displayUploadForm() {
		return "user-new-events";
	}
	
	@PostMapping("/uploadEventImage") public String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {
		
		StringBuilder fileNames = new StringBuilder();
		Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
		fileNames.append(file.getOriginalFilename());
		Files.write(fileNameAndPath, file.getBytes());
		model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
		
		return "user-new-events";
	}
}
