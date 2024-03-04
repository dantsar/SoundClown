package com.SoundClown;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AudioFileUploadController {

	private final AudioStorageService storageService;

	@Autowired
	public AudioFileUploadController(AudioStorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/allTracks")
	public String listUploadedFiles(Model model) throws IOException {
		System.out.println("(listUploadedFiles) REQUESTED TO LIST ALL TRACKS");

		// I'm going to need to modify this because serveFile refers to something else now...
		// The problem is that I'm using Thymeleaf and it expects serveFile to map a HTTP request
		model.addAttribute("files", storageService.loadAll().map(
				path -> MvcUriComponentsBuilder.fromMethodName(AudioFileUploadController.class,
						"serveFile", path.getFileName().toString()).build().toUri().toString())
				.collect(Collectors.toList()));

		return "allTracks"; // this refers to allTracks.html in resources/templates
	}

	// Copilot output (to be deleted)
	// public String playTrack(Model model) {
	// 	System.out.println("REQUESTED TO PLAY TRACK");

	// 	// Modify to refer to the right requesteTrack!
	// 	model.addAttribute("requestdTrack", storageService.loadAll().map(
	// 			path -> MvcUriComponentsBuilder.fromMethodName(AudioFileUploadController.class,
	// 					"serveFile", path.getFileName().toString()).build().toUri().toString())
	// 			.collect(Collectors.toList()));

	// 	return "playTrack"; // this refers to playTrack.html in resources/templates
	// }


	// Note to self, it's failing because I don't have the @GetMapping("/track/{filename:.+}") annotation
	// @GetMapping("/track/{filename:.+}")

	@GetMapping("/track/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		System.out.println("(serveFile) REQUESTING TO DOWNLOAD TRACK: " + filename);
		Resource file = storageService.loadAsResource(filename);

		if (file == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	// @GetMapping("/track/{filename:.+}")
	// @ResponseBody
	// public String playTrack(@PathVariable String filename) {

	// // public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
	// 	System.out.println("(playTrack) REQUESTING TO PLAY TRACK: " + filename);


	// 	// // Add another html page to display and allow the track to be downloaded
	// 	// System.out.println("REQUESTING TO DOWNLOAD TRACK: " + filename);
	// 	// Resource file = storageService.loadAsResource(filename);

	// 	// if (file == null) {
	// 	// 	return ResponseEntity.notFound().build();
	// 	// }

	// 	// return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
	// 	// 		"attachment; filename=\"" + file.getFilename() + "\"").body(file);

	// 	// System.out.println("REQUESTED TO LIST ALL TRACKS");

	// 	// // Modify to refer to the right requesteTrack!
	// 	// model.addAttribute("requestdTrack", storageService.loadAll().map(
	// 	// 		path -> MvcUriComponentsBuilder.fromMethodName(AudioFileUploadController.class,
	// 	// 				"serveFile", path.getFileName().toString()).build().toUri().toString())
	// 	// 		.collect(Collectors.toList()));

	// 	return "playTrack"; // this refers to playTrack.html in resources/templates
	// }

	@PostMapping("/uploadTrack")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		System.out.println("REQUESTED TO UPLOAD TRACK: " + file.getOriginalFilename());

		storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/allTracks";
	}

	@ExceptionHandler(AudioStorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(AudioStorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
