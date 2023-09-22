package com.space.audio;

import org.junit.Test;

public class AudioTests {

    // Error Handling
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void audio_shouldThrowArrayIndexOutOfBoundsException_ifPlayingAudioOutsideBounds() {
        Audio.playSound(99);
    }
}
