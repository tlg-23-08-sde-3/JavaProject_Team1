package com.space.client;

import com.space.GameFrame;

class Main {
    public static void main(String[] args) {
        GameFrame game = new GameFrame();
        /*
         * New frame
         * When frame is cleared (or a button continues)
         * set this frame to invisible (dispose,destroy,etc)
         * Then game.startGame();
         */
        game.startGame();
    }
}
