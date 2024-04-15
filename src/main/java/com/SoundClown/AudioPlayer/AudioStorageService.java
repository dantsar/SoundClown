package com.SoundClown;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface AudioStorageService {

	void init();

	void store(MultipartFile file);
	void storeId(MultipartFile file, Long track_id);

	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename);

	void deleteAll();

}
