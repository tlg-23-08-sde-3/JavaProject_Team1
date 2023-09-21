package com.space.audio;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AudioTests {

    @Before
    public void setUp() {

    }

    // Error Handling
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void audio_shouldThrowArrayIndexOutOfBoundsException_ifPlayingAudioOutsideBounds() {
        Audio.playSound(99);
    }
}
