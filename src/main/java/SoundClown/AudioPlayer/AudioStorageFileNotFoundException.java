package SoundClown.AudioPlayer;

public class AudioStorageFileNotFoundException extends AudioStorageException {

	public AudioStorageFileNotFoundException(String message) {
		super(message);
	}

	public AudioStorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
