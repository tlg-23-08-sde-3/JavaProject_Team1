package com.space.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {
    File file;
    AudioInputStream ais = null;
    Clip clip;

    public Audio(String fileString) {
        this.file = new File(fileString);
        loadAudioSystem();
    }

    public void playSound() {
        if (clip != null) {
            clip.start();
        }
    }

    public void closeAudioSystem() {
        try {
            clip.close();
            ais.close();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }

    }

    private void loadAudioSystem() {
        try {
            ais = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            System.out.println("Issue with loading and opening the file!\n" + e.getLocalizedMessage());
        }
    }
}
