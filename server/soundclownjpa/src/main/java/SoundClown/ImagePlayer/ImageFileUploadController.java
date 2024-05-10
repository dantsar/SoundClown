package SoundClown.ImagePlayer;

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
public class ImageFileUploadController {

    private final ImageStorageService storageService;

	@Autowired
	public ImageFileUploadController(ImageStorageService storageService) {
		this.storageService = storageService;
	}

	@PostMapping("/upload-image/{track_id}")
	public ResponseEntity<String> handleFileUploadId(@RequestParam("image_file") MultipartFile file,
        @PathVariable("track_id") Long track_id,
		RedirectAttributes redirectAttributes) {

		System.out.println("REQUESTED TO UPLOAD IMAGE: " + file.getOriginalFilename());
        System.out.println("Other info: " + track_id);

		storageService.storeId(file, track_id);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");
        return ResponseEntity.ok("File Uploaded Successfully");
	}

    @GetMapping("/download-image/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> downloadImage(@PathVariable String filename) {
		System.out.println("REQUESTING TO DOWNLOAD IMAGE: " + filename);
		Resource file = storageService.loadAsResource(filename);

		if (file == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@ExceptionHandler(ImageStorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(ImageStorageFileNotFoundException exc) {
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

