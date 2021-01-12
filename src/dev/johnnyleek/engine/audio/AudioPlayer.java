package dev.johnnyleek.engine.audio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import dev.johnnyleek.engine.util.Logger;

/**
 * Represents the AudioPlayer. This system can be used to play AudioClips.
 * Only one instance of the AudioPlayer can be active at any given time; however,
 * it will not be instantiated until it is called upon (it is loaded lazily).
 * 
 * It must be accessed in a static context: "AudioPlayer.getAudioPlayer()"
 * 
 * Every AudioClip that is being played is stored in a HashMap, keyed by
 * the clips UUID.
 * 
 * In it's current state, ALL clips that have the same ID are stopped together.
 * This means that if you play multiple versions of the same AudioClip, you must
 * stop all of them or none of them.
 * 
 * This can be avoided by creating a NEW AudioClip with the same audio source. This
 * will generate a new UUID for this separate clip so that they can be stopped
 * independently.
 * 
 * 
 * @author Johnny Leek
 * @version 1.0
 *
 */
public class AudioPlayer {

	private AudioInputStream audioIn;
	private static AudioPlayer audioPlayer;
	
	private HashMap<UUID, List<Clip>> currentClips;
	
	/**
	 * Instantiates the Audio Player
	 */
	private AudioPlayer() {
		currentClips = new HashMap<UUID, List<Clip>>();
		Logger.info("Audio player initialized!");
	}
	
	/**
	 * Gets the current instance of the AudioPlayer if one exists,
	 * otherwise instantiate a new AudioPlayer
	 * @return the instance of the AudioPlayer
	 */
	public static AudioPlayer getAudioPlayer() {
		if(audioPlayer == null) {
			audioPlayer = new AudioPlayer();
		}
		return audioPlayer;
	}
	
	/**
	 * Plays the audio file from the provided AudioClip, and adds the UUID of the clip
	 * to the list of current clips.
	 * 
	 * If the AudioClip has it's "shouldLoop" field set to true, then the clip will loop
	 * for the number of times specified in the AudioClip's "loopCount" field.
	 * @param audioClip the AudioClip to play
	 */
	public void playAudio(AudioClip audioClip) {
		Logger.debug("Playing audio clip: \"" + audioClip.getPath() + "\". ID: \"" + audioClip.getClipID() + "\"");
		
		try {
			this.audioIn = AudioSystem.getAudioInputStream(audioClip.getFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
			
			if(currentClips.containsKey(audioClip.getClipID())) {
				List<Clip> playingClips = currentClips.get(audioClip.getClipID());
				playingClips.add(clip);
				currentClips.put(audioClip.getClipID(), playingClips);
			} else {
				List<Clip> playingClips = new ArrayList<Clip>();
				playingClips.add(clip);
				currentClips.put(audioClip.getClipID(), playingClips);
			}
			
			if(audioClip.shouldLoop()) {
				Logger.debug("Looping audio clip: \"" + audioClip.getPath() + "\". ID: \"" + audioClip.getClipID() + "\"");
				clip.loop(audioClip.getLoopCount());
			}
			
		} catch (UnsupportedAudioFileException e) {
			Logger.error("Unsupported Audio File!");
			e.printStackTrace();
		} catch (IOException e) {
			Logger.error("IO Exception occurred trying to play audio file");
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			Logger.error("Audio line unavailable!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Stops ALL instances of provided audio clip from playing.
	 * That is, it shall stop every clip that matches the UUID of the
	 * AudioClip provided
	 * @param audioClip the AudioClip to stop
	 */
	public void stopAudio(AudioClip audioClip) {
		List<Clip> clips = currentClips.get(audioClip.getClipID());
		for(Clip clip : clips) {
			clip.stop();
			clip.flush();
			clip.close();
			try {
				audioClip.closeFile();
			} catch (IOException e) {
				Logger.error("Failed to close audio input stream for clip \"" + audioClip.getPath() + "\". ID: \"" + audioClip.getClipID() + "\"");
				e.printStackTrace();
			}
		}
	}
	
}
