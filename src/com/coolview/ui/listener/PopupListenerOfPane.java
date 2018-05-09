package com.coolview.ui.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.Key;

import javax.swing.JPopupMenu;

import com.coolview.ui.MainWindow;
import com.coolview.ui.menus.RepaintPane;
import com.coolview.ui.panes.ImageLabel;

import sun.applet.Main;


public class PopupListenerOfPane extends AbstractListener  {
    JPopupMenu popup;

    public PopupListenerOfPane(JPopupMenu popupMenu) {
        popup = popupMenu;

    }

    public void mousePress(MouseEvent e) {
        if (MainWindow.choosedImg != null) {
            MainWindow.choosedImg.setOpaque(false);
            MainWindow.choosedImg.repaint();
        }
        maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
        if (MainWindow.choosedImg != null) {
            MainWindow.choosedImg.setOpaque(false);
            MainWindow.choosedImg.repaint();
        }
        maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) {
        // editFile = MainWindow.choosedImgFile;

        if (e.isPopupTrigger()) { // 右键
            System.out.println("panel上的右键");
            System.out.println(MainWindow.curNodePath);
            popup.show(e.getComponent(), e.getX(), e.getY());
        }else{
            if (MainWindow.isSelectAll == true){
                MainWindow.isSelectAll = false;
//                new RepaintPane().execute();
                for (ImageLabel i : MainWindow.labelList) {
                    i.setBorder(null);
                    i.setBackground(null);
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("按下："+KeyEvent.getKeyText(e.getKeyCode()) + "\n");
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("松开：" + KeyEvent.getKeyText(e.getKeyCode()) + "\n");
        
    }
}
