package com.coolview.ui.listener;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JPopupMenu;

import com.coolview.ui.MainWindow;
import com.coolview.ui.menus.ManagerMenu;
import com.coolview.ui.panes.ImageLabel;
import com.sun.prism.Image;


public class PopupListener extends MouseAdapter {
    private JPopupMenu popup;
    private ImageLabel imageLabel;
    private File editFile;

    public PopupListener(JPopupMenu popupMenu, ImageLabel imageLabel, File editFile) {
        this.popup = popupMenu;
        this.imageLabel = imageLabel;
        this.editFile = editFile;
    }

    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) { // 右键
            System.out.println("右键");
            if (imageLabel != null) {
                imageLabel.setBackground(new Color(193, 230, 249));
                imageLabel.setBorder(BorderFactory.createLineBorder(new Color(163, 230, 249), 1));
                imageLabel.setOpaque(true); // 这里是必须的

                if (!MainWindow.renameFile.isEmpty() && MainWindow.renameFile.containsKey(editFile)) // 已被重命名，换为姓名字
                    editFile = MainWindow.renameFile.get(editFile);

                MainWindow.choosedImgFile = editFile;
                MainWindow.statusbar.setText("  " + MainWindow.choosedImgFile.getAbsolutePath());
                if (MainWindow.choosedImg != null && MainWindow.choosedImg != imageLabel) {
                    MainWindow.choosedImg.setOpaque(false); // 这里是必须的
                    MainWindow.choosedImg.setBorder(null);
                }
                MainWindow.choosedImg = imageLabel;
            }
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // System.out.println("click");
        imageLabel.setBackground(new Color(193, 230, 249));
        imageLabel.setBorder(BorderFactory.createLineBorder(new Color(163, 230, 249), 1));
        imageLabel.setOpaque(true); // 这里是必须的
        if (!MainWindow.renameFile.isEmpty() && MainWindow.renameFile.containsKey(editFile)) // 已被重命名，换为姓名字
            editFile = MainWindow.renameFile.get(editFile);

        MainWindow.choosedImgFile = editFile;
        MainWindow.statusbar.setText("  " + MainWindow.choosedImgFile.getAbsolutePath());
        if (MainWindow.choosedImg != null && MainWindow.choosedImg != imageLabel) {
            MainWindow.choosedImg.setOpaque(false); // 这里是必须的
            MainWindow.choosedImg.setBorder(null);
        }
        MainWindow.choosedImg = imageLabel;
        if (e.getClickCount() >= 2) {
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    MainWindow.createViewFrame(editFile);
                }
            });
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (MainWindow.choosedImg == imageLabel)
            return;
        imageLabel.setBackground(new Color(0, 230, 249));//215
        imageLabel.setOpaque(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (MainWindow.choosedImg == imageLabel)
            return;
        imageLabel.setBackground(null);
        imageLabel.setOpaque(false);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }
}
