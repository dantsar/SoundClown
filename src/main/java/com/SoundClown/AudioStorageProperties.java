package com.SoundClown;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("storage")
public class AudioStorageProperties {

	/**
	 * Folder location for storing files
	 */
	private String location = "audio_files";

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
