package com.space;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class KeyHandler implements KeyListener {

    public static boolean upPressed, leftPressed, rightPressed, shootPressed;
    public static boolean upReleased, leftReleased, rightReleased, shootReleased;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code){
            case KeyEvent.VK_UP:
                upPressed = true;
                break;
            case KeyEvent.VK_LEFT:
                leftPressed = true;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = true;
                break;
            case KeyEvent.VK_SPACE:
                shootPressed = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code){
            case KeyEvent.VK_UP:
                upReleased = true;
                break;
            case KeyEvent.VK_LEFT:
                leftReleased = true;
                break;
            case KeyEvent.VK_RIGHT:
                rightReleased = true;
                break;
            case KeyEvent.VK_SPACE:
                shootReleased = true;
                break;
        }
    }
}
