package com.coolview.ui.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.coolview.ui.MainWindow;

public class PaneKeyListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            MainWindow.pressedCtrl = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            MainWindow.pressedCtrl = false;
        }

    }

}
