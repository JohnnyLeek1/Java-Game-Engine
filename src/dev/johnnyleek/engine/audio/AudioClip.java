package dev.johnnyleek.engine.audio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import javax.sound.sampled.Clip;

import dev.johnnyleek.engine.util.Logger;

/**
 * Represents an playable segment of audio.
 * 
 * Audio clips are provided with a randomly generated UUID, which can be 
 * used to identify the audio clip in order to stop the clip.
 * 
 * Audio clips MUST be a .wav file in order to be played.
 * 
 * Clips can also be looped, and a loop counter can be provided.
 * If no counter is provided, the clip will loop indefinitely.
 * 
 * The path of the clip can be changed, but the audio will not be
 * changed until it is stopped and started again (using the AudioPlayer's
 * "stopAudio" and "playAudio" methods.
 * 
 * The constructor for AudioClip throws a FileNotFoundException which must be
 * handled. This is thrown in the event that the provided file path does not exist.
 * 
 * @author Johnny Leek
 * @version 1.0
 *
 */
public class AudioClip {

	private UUID clipId;
	private BufferedInputStream file;
	private String path;
	
	private boolean shouldLoop = false;
	private int loopCount = Clip.LOOP_CONTINUOUSLY;

	/**
	 * Provides a constructor for instantiating an AudioClip that does not loop.
	 * @param path the file path to the audio clip
	 * @throws FileNotFoundException if the file cannot be found
	 */
	public AudioClip(String path) throws FileNotFoundException {
		this.path = path;
		this.file = new BufferedInputStream(new FileInputStream(path));
		this.clipId = UUID.randomUUID();
	}
	
	/**
	 * Provides a constructor for instantiating an AudioClip that should loop infinitely (by default)
	 * @param path the file path to the clip
	 * @param shouldLoop whether or not the AudioClip should loop
	 * @throws FileNotFoundException if the file cannot be found
	 */
	public AudioClip(String path, boolean shouldLoop) throws FileNotFoundException {
		this.path = path;
		this.file = new BufferedInputStream(new FileInputStream(path));
		this.shouldLoop = true;
		this.clipId = UUID.randomUUID();
	}
	
	/**
	 * Provides a constructor for instantiating an AudioClip that should loop for a defined amount of times (by default)
	 * @param path the file path to the clip
	 * @param shouldLoop whether or not the AudioClip should loop
	 * @param loopCount the number of times the clip should loop
	 * @throws FileNotFoundException if the file cannot be found
	 */
	public AudioClip(String path, boolean shouldLoop, int loopCount) throws FileNotFoundException {
		this.path = path;
		this.file = new BufferedInputStream(new FileInputStream(path));
		this.shouldLoop = true;
		this.loopCount = loopCount;
		this.clipId = UUID.randomUUID();
	}

	/**
	 * Gets the UUID of the clip
	 * @return the UUID of the clip
	 */
	public UUID getClipID() {
		return this.clipId;
	}
	
	/**
	 * Creates a new BufferedInputStream for the file path
	 * BufferedInputStream supports mark/reset, which lets it
	 * be played by the AudioSystem.
	 * 
	 * Terminates program if the file cannot be found
	 * 
	 * @return a BufferedInputStream constructed from the file path
	 */
	public BufferedInputStream getFile() {	
		try {
			return new BufferedInputStream(new FileInputStream(this.path));
		} catch (FileNotFoundException e) {
			Logger.error("File not found: \"" + this.path + "\"");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Closes the current audio file
	 * @throws IOException if the file cannot be closed
	 */
	public void closeFile() throws IOException {
		this.file.close();
	}
	
	/**
	 * Returns the file path of the AudioClip
	 * @return the current file path of the AudioClip
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * Returns whether or not the clip should loop
	 * @return true/false depending on if the clip should loop
	 */
	public boolean shouldLoop() {
		return this.shouldLoop;
	}

	/**
	 * Enables/disables the AudioClip from looping
	 * @param shouldLoop whether or not the clip should loop
	 */
	public void setShouldLoop(boolean shouldLoop) {
		this.shouldLoop = shouldLoop;
	}

	/**
	 * Gets the current loop count
	 * @return the current loop count
	 */
	public int getLoopCount() {
		return loopCount;
	}

	/**
	 * Sets the loop count for the AudioClip
	 * @param loopCount how many times the AudioClip should loop before stopping
	 */
	public void setLoopCount(int loopCount) {
		this.loopCount = loopCount;
	}	
	
}
