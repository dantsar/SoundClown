package SoundClown.ImagePlayer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("storage")
public class ImageStorageProperties {

	/**
	 * Folder location for storing files
	 */
	private String location = "/tmp/image_files";

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}

