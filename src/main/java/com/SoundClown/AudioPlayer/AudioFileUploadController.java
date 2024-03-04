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

/*
	Current behavior that I'm looking to implement:
		* allTracks:
			- List all tracks (each track redirects to /track/{filename:.+} which will give you the option to play or download the track
			- when I play the track, I want to dynamicaslly load the track and play it (I don't want to download everything because it's not scalable)
		* track/{filename:.+}
			- Play or download the track, this is loosly based on the example that I saw


	The behavior that I'm looking to implement is the following:
	* /allTracks: I want this page to list all the saved tracks. The list includes a link to /track/{filename:+} and the audio file in the browser (I want this to serve allTracks.html)
	* /track/{filename+}: I want this page to provide a link to download the audio file as well as play the audio file in the browser. (I want this to serve playTrack.html)

	my biggest confusion is how do I manage the Thymeleaf model. serveFile (which is \@GetMapping ("/track/{filename:+}" is used to render everything in allTracks/.
*/

// AudioFileUploadController.class, "serveTrack", path.getFileName().toString()).build().toUri().toString()

@Controller
public class AudioFileUploadController {

	private final AudioStorageService storageService;

	@Autowired
	public AudioFileUploadController(AudioStorageService storageService) {
		this.storageService = storageService;
	}

    // This is such a stuuupid way of going about this
    @GetMapping("/allTracks")
    public String listUploadedFiles(Model model) throws IOException {
        System.out.println("(listUploadedFiles) REQUESTED TO LIST ALL TRACKS");

        model.addAttribute("files", storageService.loadAll().map(
                path -> path.getFileName().toString()));


        // model.addAttribute("files", storageService.loadAll().map(
        //         path -> MvcUriComponentsBuilder.fromMethodName(AudioFileUploadController.class,
        //                 "downloadTrack", path.getFileName().toString()).build().toUri().toString())
        //         .collect(Collectors.toList()),
        //         "filenames", storageService.loadAll().map(
        //             path -> path.getFileName().toString()).collect(Collectors.toList());


        // model.addAttribute("files", storageService.loadAll().map(
        //     path -> path.getFileName().toString().collect(Collectors.toList()));

        return "allTracks"; // this refers to allTracks.html in resources/templates
    }



    // @GetMapping("/listTrack/{filename:.+}")
    // @ResponseBody
    // public String listTrack(@PathVariable String filename) {
    //     System.out.println("(listTrack) REQUESTED TO LIST TRACK: " + filename);

    //     // model.addAttribute("filename", filename);
    //     return "playTrack"; // this refers to playTrack.html in resources/templates
    // }


	@PostMapping("/uploadTrack")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
        @RequestParam("title") String track_name,
        @RequestParam("artist") String artist,
		RedirectAttributes redirectAttributes) {

		System.out.println("REQUESTED TO UPLOAD TRACK: " + file.getOriginalFilename());
        System.out.println("Other info: " + track_name + " " + artist);

		storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/allTracks";
	}



    // this is the page for playing a selected track
    @GetMapping("/track/{filename:.+}")
    public String serveTrack(Model model, @PathVariable String filename) {
        System.out.println("(serveTrack) REQUESTING TO PLAY TRACK: " + filename);

        Resource file = storageService.loadAsResource(filename);
        if (file != null) {
            model.addAttribute("filename", filename);
            return "playTrack"; // Uses playTrack.html
        }

        // return "redirect:/helloClasss"; // File not found, redirect to all tracks "you fucked up..."
        return "redirect:/allTracks"; // File not found, redirect to all tracks
    }

    @GetMapping("/download/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> downloadTrack(@PathVariable String filename) {
		System.out.println("REQUESTING TO DOWNLOAD TRACK: " + filename);
		Resource file = storageService.loadAsResource(filename);

		if (file == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@ExceptionHandler(AudioStorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(AudioStorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
