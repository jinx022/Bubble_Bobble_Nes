package it.metodologie.bubblebobblenes.singleton;

import javazoom.jl.player.Player;
import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to play music and sounds effects
 */
public class AudioManager {
    /** Singleton instance of the AudioManager */
    private static AudioManager instance;
    /** Player for MP3 format audio files */
    private Player mp3Player;
    /** Player for WAV format audio files */
    private Clip wavClip;
    /** Map containing all background music tracks */
    private Map<String, AudioTrack> musicTracks;
    /** Map containing all sound effects */
    private Map<String, AudioTrack> soundEffects;
    /** Flag indicating if audio is currently muted */
    private boolean isMuted;
    /** Current volume level (0.0 to 1.0) */
    private float volume;
    /** Thread for handling background music playback */
    private Thread musicThread;

    /**
     * Inner class representing an audio track with its path and format
     */
    private static class AudioTrack {
        /** Path to the audio file */
        String path;
        /** Flag indicating if the audio is in MP3 format */
        boolean isMP3;

        /**
         * Creates a new audio track
         *
         * @param path Path to the audio file
         * @param isMP3 true if the audio is MP3 format, false for WAV
         */
        AudioTrack(String path, boolean isMP3) {
            this.path = path;
            this.isMP3 = isMP3;
        }
    }

    /**
     * Private constructor to prevent direct instantiation.
     * Initializes the audio system with default settings.
     */
    private AudioManager() {
        musicTracks = new HashMap<>();
        soundEffects = new HashMap<>();
        volume = 0.5f;
        isMuted = false;
        initializeAudio();
    }

    /**
     * Returns the singleton instance of AudioManager
     *
     * @return The AudioManager instance
     */
    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    /**
     * Initializes the audio tracks and sound effects with their respective paths
     */
    private void initializeAudio() {
        musicTracks.put("menu", new AudioTrack("/audio/title.mp3", true));
        musicTracks.put("game", new AudioTrack("/audio/pressing_forward.mp3", true));
        musicTracks.put("victory", new AudioTrack("/audio/victory.mp3", true));

        soundEffects.put("jump", new AudioTrack("/audio/jump.wav", false));
        soundEffects.put("bubble", new AudioTrack("/audio/bubble.wav", false));
        soundEffects.put("powerup", new AudioTrack("/audio/jump.wav", false));
        soundEffects.put("enemy_defeat", new AudioTrack("/audio/enemy.wav", false));
    }

    /**
     * Plays a music track with option for looping
     *
     * @param trackName Name of the track to play (must exist in musicTracks)
     * @param loop true to loop the track continuously, false to play once
     */
    public void playMusic(String trackName, boolean loop) {
        if (musicTracks.containsKey(trackName)) {
            stopMusic();

            if (isMuted) return;

            AudioTrack track = musicTracks.get(trackName);

            musicThread = new Thread(() -> {
                try {
                    do {
                        InputStream is = getClass().getResourceAsStream(track.path);
                        BufferedInputStream bis = new BufferedInputStream(is);

                        if (track.isMP3) {
                            mp3Player = new Player(bis);
                            mp3Player.play();
                        } else {
                            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bis);
                            wavClip = AudioSystem.getClip();
                            wavClip.open(audioStream);
                            wavClip.setFramePosition(0);
                            if (loop) {
                                wavClip.loop(Clip.LOOP_CONTINUOUSLY);
                            } else {
                                wavClip.start();
                            }
                        }
                        if (!loop) break;
                    } while (!Thread.interrupted());
                } catch (Exception e) {
                    System.err.println("Error playing music: " + e.getMessage());
                }
            });

            musicThread.start();
        }
    }

    /**
     * Plays a music track with default looping behavior (continuous loop)
     *
     * @param trackName Name of the track to play (must exist in musicTracks)
     */
    public void playMusic(String trackName) {
        playMusic(trackName, true);
    }

    /**
     * Stops currently playing music track and cleans up resources
     */
    public void stopMusic() {
        if (musicThread != null && musicThread.isAlive()) {
            musicThread.interrupt();
        }
        if (mp3Player != null) {
            mp3Player.close();
        }
        if (wavClip != null) {
            wavClip.stop();
            wavClip.close();
        }
    }

    /**
     * Plays a sound effect once
     *
     * @param effectName Name of the sound effect to play (must exist in soundEffects)
     */
    public void playSoundEffect(String effectName) {
        if (!isMuted && soundEffects.containsKey(effectName)) {
            try {
                AudioTrack track = soundEffects.get(effectName);
                InputStream audioSrc = getClass().getResourceAsStream(track.path);

                if (audioSrc == null) {
                    System.err.println("Could not find audio file: " + track.path);
                    return;
                }

                if (track.isMP3) {
                    new Thread(() -> {
                        try {
                            Player player = new Player(new BufferedInputStream(audioSrc));
                            player.play();
                            player.close();
                        } catch (Exception e) {
                            System.err.println("Error playing MP3 effect: " + e.getMessage());
                        }
                    }).start();
                } else {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                            new BufferedInputStream(audioSrc));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();

                    clip.addLineListener(event -> {
                        if (event.getType() == LineEvent.Type.STOP) {
                            clip.close();
                        }
                    });
                }

            } catch (Exception e) {
                System.err.println("Error playing sound effect: " + e.getMessage());
            }
        }
    }
}