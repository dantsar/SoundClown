package com.SoundClown;

import java.io.IOException;
import java.util.stream.Collectors;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@CrossOrigin
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

        model.addAttribute("files", storageService.loadAll().map(
                path -> path.getFileName().toString()));

        return "allTracks"; // this refers to allTrack.html in resources/templates
    }

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

		return "redirect:/allTrack";
	}

    //alternative upload
	@PostMapping("/upload/{track_id}")
	public ResponseEntity<String> handleFileUploadId(@RequestParam("file") MultipartFile file,
        @PathVariable("track_id") Long track_id,
		RedirectAttributes redirectAttributes) {

		System.out.println("REQUESTED TO UPLOAD TRACK: " + file.getOriginalFilename());
        System.out.println("Other info: " + track_id);

		storageService.storeId(file, track_id);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");
        return ResponseEntity.ok("File Uploaded Successfully");
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

    public static class FileRequest {
        private MultipartFile file;
        private Long track_id;

        public MultipartFile getFile() {
            return file;
        }

        public Long getTrackId() {
            return track_id;
        }
    }
}
