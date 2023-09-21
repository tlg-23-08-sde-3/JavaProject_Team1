package com.space.audio;

import javax.sound.sampled.*;
import java.io.File;

public class Audio {

    // static fields
    private static String[] assets = {
            "assets/audio/laserShoot.wav",
            "assets/audio/explosion.wav",
            "assets/audio/mainmenuloop.wav",
            "assets/audio/mainloopsong.wav",
            "assets/audio/EndSong.wav"};
    private static File[] file;
    private static AudioInputStream[] ais = null;
    private static Clip[] clip;

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

    public static void touch() {
        // simply loading static class
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

//    public Audio(String fileString) {
//        this.file = new File(fileString);
//        loadAudioSystem();
//    }
//
//    public synchronized void playSound() {
//        if (clip != null) {
//            clip.start();
//        }
//    }
//
//    public void closeAudioSystem() {
//        try {
//            clip.close();
//            ais.close();
//        } catch (IOException e) {
//            System.out.println(e.getLocalizedMessage());
//        }
//
//    }
//
//    private void loadAudioSystem() {
//        try {
//            ais = AudioSystem.getAudioInputStream(file);
//            clip = AudioSystem.getClip();
//            clip.open(ais);
//        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
//            System.out.println("Issue with loading and opening the file!\n" + e.getLocalizedMessage());
//        }
//    }
}
