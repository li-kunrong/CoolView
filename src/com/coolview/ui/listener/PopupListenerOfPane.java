package com.coolview.ui.listener;

import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

import com.coolview.ui.MainWindow;
import com.coolview.ui.panes.ImageLabel;


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

}
