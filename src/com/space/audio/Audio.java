package com.space.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Static Audio class
 * Loads audio from assets folder
 * Allows you to play, stop and loop audio
 */
public class Audio {

    // static fields
    private static final String[] assets = {
            "assets/audio/laserShoot.wav",
            "assets/audio/explosion.wav",
            "assets/audio/mainmenuloop.wav",
            "assets/audio/mainloopsong.wav",
            "assets/audio/EndSong.wav"};
    private static final File[] file;
    private static AudioInputStream[] ais = null;
    private static final Clip[] clip;

    // constructors
    private Audio() {
        // no-op
    }

    // action methods
    public static void playSound(int index) {
        stopSound(index);
        clip[index].start();
    }

    public static void stopSound(int index) {
        clip[index].stop();
        clip[index].setFramePosition(0);
    }

    public static void loopSound(int index) {
        clip[index].loop(Clip.LOOP_CONTINUOUSLY);
    }

    static {
        file = new File[assets.length];
        clip = new Clip[assets.length];
        ais = new AudioInputStream[assets.length];
        for (int i = 0; i < assets.length; i++) {
            try {
                file[i] = new File(assets[i]);
                ais[i] = AudioSystem.getAudioInputStream(file[i]);
                clip[i] = AudioSystem.getClip();
                clip[i].open(ais[i]);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }
}
