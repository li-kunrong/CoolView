package com.coolview.ui.listener;

import static org.hamcrest.CoreMatchers.sameInstance;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPopupMenu;

import com.coolview.ui.MainWindow;
import com.coolview.ui.menus.RepaintPane;
import com.coolview.ui.panes.ImageLabel;



public class PopupListener extends AbstractListener {
    private JPopupMenu popup;
    private ImageLabel imageLabel;
    private File editFile;

    private static boolean mulChoice = false;
    private boolean mulPressed = false;
    private ArrayList<ImageLabel> jLabels;
    private ImageLabel lastImage;

    public PopupListener(JPopupMenu popupMenu, ImageLabel imageLabel, File editFile) {
        this.popup = popupMenu;
        this.imageLabel = imageLabel;
        this.editFile = editFile;
    }

    private void maybeShowPopup(MouseEvent e) {
        if (MainWindow.isSelectAll) {
            MainWindow.isSelectAll = false;
            for (ImageLabel i : MainWindow.labelList) {
                i.setBorder(null);
                i.setBackground(null);
            }
        }
        if (e.isPopupTrigger()) { // 右键
            System.out.println("右键");
            if (imageLabel != null) {
                imageLabel.setBackground(new Color(193, 230, 249));
                imageLabel.setBorder(BorderFactory.createLineBorder(new Color(163, 230, 249), 1));
                imageLabel.setOpaque(true); // 这里是必须的

                if (!MainWindow.renameFile.isEmpty() && MainWindow.renameFile.containsKey(editFile)) // 已被重命名，换为姓名字
                    editFile = MainWindow.renameFile.get(editFile);

                MainWindow.choosedImgFile = editFile;
//                MainWindow.statusbar.setText("  " + MainWindow.choosedImgFile.getAbsolutePath());
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
         System.out.println("click");
         jLabels = MainWindow.labelList;//获取当前的jlabel数组
         mulPressed = MainWindow.pressedCtrl;//当前是否按压了ctrl
         if(!mulPressed) {//没按压ctrl只能单选
//             if(lastImage != null) {
//                 //看是否点击了上一个jlabel，如果是，则border设置为空，
//                 //如果不是，上个jlabel的border设置为空，此jlabelborder设置为选中
                 for (ImageLabel i : MainWindow.labelList) {
                     i.setBorder(null);
                     i.setBackground(null);
                 }
                 
//             }
             imageLabel.setBackground(new Color(0, 230, 249));
             imageLabel.setBorder(BorderFactory.createLineBorder(new Color(215, 230, 249), 2));
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
             MainWindow.selectList.clear();
//             MainWindow.selectList.clear();
             MainWindow.selectList.add(imageLabel.getImageFile());
             MainWindow.statusbar.setText("选中了"+MainWindow.selectList.size() + "张图片");
             lastImage = imageLabel;
         }else{
             if (imageLabel.getBorder()!=null){
                 imageLabel.setBorder(null);
                 imageLabel.setBackground(null);
                 MainWindow.selectList.remove(imageLabel.getImageFile());
                 MainWindow.statusbar.setText("选中了"+MainWindow.selectList.size() + "张图片");
             }else{
                 imageLabel.setBorder(BorderFactory.createLineBorder(new Color(163, 230, 249), 2));
                 imageLabel.setBackground(new Color(0, 230, 249));
                 MainWindow.selectList.add(imageLabel.getImageFile());
                 MainWindow.statusbar.setText("选中了"+MainWindow.selectList.size() + "张图片");
             }
         }
       
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
        imageLabel.setBackground(new Color(215, 230, 249));//215
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
       
//        maybeShowPopup(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

   

}
