package SoundClown.ImagePlayer;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@Service
public class ImageFileSystemStorageService implements ImageStorageService {

	private final Path rootLocation;

	@Autowired
	public ImageFileSystemStorageService(ImageStorageProperties properties) {

        if(properties.getLocation().trim().length() == 0){
            throw new ImageStorageException("File upload location can not be Empty.");
        }

		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new ImageStorageException("Failed to store empty file.");
			}
			Path destinationFile = this.rootLocation.resolve(
					Paths.get(file.getOriginalFilename()))
					.normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check
				throw new ImageStorageException(
						"Cannot store file outside current directory.");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new ImageStorageException("Failed to store file.", e);
		}
	}

	@Override
    public void storeId(MultipartFile file, Long track_id) {
        try {
            if (file.isEmpty()) {
                throw new ImageStorageException("Failed to store empty file.");
            }
            String newFileName = "image_" + track_id + ".png";
            Path destinationFile = this.rootLocation.resolve(Paths.get(newFileName)).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new ImageStorageException("Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                // Resize and crop the image
                Thumbnails.of(inputStream)
                    .size(64, 64)
                    .crop(Positions.CENTER)
                    .toFile(destinationFile.toFile());
            }
        } catch (IOException e) {
            throw new ImageStorageException("Failed to store file.", e);
        }
    }

	@Override
	public Stream<Path> loadAll() {
		// Create a stream of file paths of all the tracks in "./image_files"
		try {
			return Files.walk(this.rootLocation, 1)
				.filter(path -> !path.equals(this.rootLocation))
				.map(this.rootLocation::relativize);
		}
		catch (IOException e) {
			throw new ImageStorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new ImageStorageFileNotFoundException(
						"Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new ImageStorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		// try {
		// 	Files.createDirectories(rootLocation);
		// }
		// catch (IOException e) {
		// 	throw new ImageStorageException("Could not initialize storage", e);
		// }
        System.out.println("Init ImageFileSystemStorageService");
	}
}

