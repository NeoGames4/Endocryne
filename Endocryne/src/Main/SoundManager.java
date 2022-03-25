package Main;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {
	
	private long punchSounds = 0;
	private long playerTakesDamageSounds = 0;
	private long mobTakesDamageSounds = 0;
	private long playerDiesSounds = 0;
	private long mobDiesSounds = 0;
	private long stepSounds = 0;
	
	private File punchSoundOne;
	private File punchSoundTwo;
	private File playerTakesDamageSound;
	private File mobTakesDamageSound;
	private File playerDiesSound;
	private File mobDiesSound;
	private File stepSoundsClip;
	
	public SoundManager() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		punchSoundOne = new File("./rsc/punchOne.wav");
		punchSoundTwo = new File("./rsc/punchTwo.wav");
		playerTakesDamageSound = new File("./rsc/playerTakesDamage.wav");
		mobTakesDamageSound = new File("./rsc/mobTakesDamage.wav");
		playerDiesSound = new File("./rsc/playerDies.wav");
		mobDiesSound = new File("./rsc/mobDies.wav");
		stepSoundsClip = new File("./rsc/steps.wav");
	}
	
	public void playPunchSounds() {
		if(System.currentTimeMillis() - punchSounds > 300) {
			punchSounds = System.currentTimeMillis();
			try {
				play(Math.random() > 0.5 ? punchSoundOne : punchSoundTwo, 0);
			} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void playPlayerTakesDamageSounds() {
		if(System.currentTimeMillis() - playerTakesDamageSounds > 100) {
			playerTakesDamageSounds = System.currentTimeMillis();
			try {
				play(playerTakesDamageSound, 0);
			} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void playMobTakesDamageSounds() {
		if(System.currentTimeMillis() - mobTakesDamageSounds > 100) {
			mobTakesDamageSounds = System.currentTimeMillis();
			try {
				play(mobTakesDamageSound, 0);
			} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void playPlayerDiesSounds() {
		if(System.currentTimeMillis() - playerDiesSounds > 100) {
			playerDiesSounds = System.currentTimeMillis();
			try {
				play(playerDiesSound, 0);
			} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void playMobDiesSounds() {
		if(System.currentTimeMillis() - mobDiesSounds > 100) {
			mobDiesSounds = System.currentTimeMillis();
			try {
				play(mobDiesSound, 0);
			} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void playStepSounds() {
		if(System.currentTimeMillis() - stepSounds > 200) {
			stepSounds = System.currentTimeMillis();
			try {
				play(stepSoundsClip, 0);
			} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Clip play(File soundFile, int loop) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		AudioInputStream stream = AudioSystem.getAudioInputStream(soundFile);
		DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat());
		Clip clip = (Clip)AudioSystem.getLine(info);
		clip.open(stream);
		clip.loop(loop);
		return clip;
	}

}
