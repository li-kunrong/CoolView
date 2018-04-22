package com.coolview.logic;

import java.awt.Desktop;
import java.awt.Frame;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.coolview.ui.MainWindow;
import com.coolview.ui.frames.InfoFrame;
import com.coolview.ui.menus.ManagerMenu;
import com.coolview.ui.panes.ImageLabel;
import com.coolview.ui.panes.ShowAllPane;

import sun.applet.Main;

public class EditPhoto {

    public void view() {
        if (MainWindow.choosedImg != null) {
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    MainWindow.createViewFrame(MainWindow.choosedImgFile);
                }
            });
        }

    }

    public void getInfo(File chooseFile, File editfile) {
        if (MainWindow.choosedImgFile == null)
            return;
        if (!MainWindow.curNodePath.equals(new File(BasicFunction.getLocation(chooseFile.getAbsolutePath()))))
            return;
        new InfoFrame(editfile).createAttFrame();

    }

    public void getPath(Frame frame) {
        File curFile = MainWindow.choosedImgFile;
        if (curFile == null)
            return;
        String path = BasicFunction.getLocation(curFile.getAbsolutePath());
        Desktop desktop = Desktop.getDesktop();
        if (!desktop.isSupported(Desktop.Action.OPEN))
            JOptionPane.showMessageDialog(frame, "抱歉！文件路径打开出错");
        try {
            desktop.open(new File(path));
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(frame, "抱歉！文件路径打开出错");
            e1.printStackTrace();
        }

    }

    public void delete(File chooseFile, ImageLabel choosedImage) {
        if (chooseFile == null)
            return;
        chooseFile.delete();

        MainWindow.curShowAllPane.remove(choosedImage);
        MainWindow.curShowAllPane.repaint();
        MainWindow.curShowAllPane.validate();

        MainWindow.imagesList.remove(chooseFile);
        MainWindow.choosedImgFile = null;
        MainWindow.choosedImg = null;

    }

    public void rename(Frame frame, File editfile, File chooseFile) {
        editfile = MainWindow.choosedImgFile;

        if (!MainWindow.renameFile.isEmpty() && MainWindow.renameFile.containsKey(editfile)) // 已被重命名，换为姓名字
            editfile = MainWindow.renameFile.get(editfile);
        if (editfile == null)
            return;
        String newFileName;

        newFileName = JOptionPane.showInputDialog(frame, "请输入新文件名称：",
                BasicFunction.getNameWithOutExtension(editfile.getName()));
        if (newFileName == null || newFileName.equals(BasicFunction.getNameWithOutExtension(editfile.getName())))
            return;
        while (!BasicFunction.isLegalName(editfile, newFileName, MainWindow.curNodePath)) {
            newFileName = JOptionPane.showInputDialog(frame, "请输入新文件名称：",
                    BasicFunction.getNameWithOutExtension(editfile.getName()));
            if (newFileName == null || newFileName.equals(BasicFunction.getNameWithOutExtension(editfile.getName())))
                return;
        }
        MainWindow.imagesList.remove(chooseFile);

        File newFile = BasicFunction.rename(chooseFile, newFileName);

        MainWindow.renameFile.put(editfile, newFile);
        editfile = newFile;

        MainWindow.choosedImgFile = editfile;
        MainWindow.imagesList.add(MainWindow.choosedImgFile);

        MainWindow.choosedImg.setImageFile(MainWindow.choosedImgFile);

    }

    public void copy(ShowAllPane editPane) {
        editPane = MainWindow.curShowAllPane;
        MainWindow.needDeleted = false;
        MainWindow.ishasEctype = true;

    }

    public void paste(Frame frame, File editfile) {
        if (!MainWindow.ishasEctype)
            return;
        System.out.println("进入粘贴");
        File targetDir = MainWindow.curNodePath;
        System.out.println(targetDir);
        System.out.println(editfile);
        if (targetDir == null || editfile == null) {
            return;
        }
        System.out.println("粘贴二层");
        if (!editfile.exists()) {
            JOptionPane.showMessageDialog(null, "粘贴文件：" + editfile.getName() + " 已不存在", "警告",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        String newFileName;
        if (MainWindow.needDeleted) {
            if (targetDir.equals(new File(BasicFunction.getLocation(editfile.getAbsolutePath())))) {
                JOptionPane.showMessageDialog(null, "目标目录和文件所在目录相同", "跳过", JOptionPane.WARNING_MESSAGE);
                return;
            }
            newFileName = BasicFunction.getNameWithOutExtension(editfile.getName());
            newFileName = BasicFunction.pasteImg(editfile, newFileName, targetDir);
            if (newFileName == null) {
                return;
            }
            ChangeRateAndShowOnPane(newFileName);

            editfile.delete();
            MainWindow.imagesList.remove(editfile);

        } else {
            newFileName = JOptionPane.showInputDialog(frame, "请输入新文件名称",
                    BasicFunction.getNameWithOutExtension(editfile.getName()));
            if (newFileName == null) {
                return;
            }
            while (!BasicFunction.isLegalName(editfile, newFileName, targetDir)) {
                if (newFileName == null)
                    return;
                newFileName = JOptionPane.showInputDialog(frame, "请输入新文件名称",
                        BasicFunction.getNameWithOutExtension(editfile.getName()));
            }
            newFileName = BasicFunction.getNameWithOutExtension(editfile.getName());
            newFileName = BasicFunction.pasteImg(editfile, newFileName, targetDir);
            if (newFileName == null) {
                return;
            }
            ChangeRateAndShowOnPane(newFileName);
            File file = new File(newFileName);
            MainWindow.imagesList.add(file);
        }

    }

    private void ChangeRateAndShowOnPane(String newFileName) {
        ImageIcon imageIcon = new ImageIcon(newFileName);
        int imgWidth = imageIcon.getIconWidth();
        int imgHeight = imageIcon.getIconHeight();

        double changeRate = 1.0 * MainWindow.curImgWidth / imgWidth < 1.0 * MainWindow.curImgHeight / imgHeight
                ? 1.0 * MainWindow.curImgWidth / imgWidth : 1.0 * MainWindow.curImgHeight / imgHeight;
        int imgChangeWidth = (int) (imgWidth * changeRate);
        int imgChangeHeight = (int) (1.0 * imgChangeWidth / imgWidth * imgHeight);
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(imgChangeWidth, imgChangeHeight, Image.SCALE_SMOOTH));
        File file = new File(newFileName);
        ImageLabel imageLabel = new ImageLabel(imageIcon, file);
        ManagerMenu menu = new ManagerMenu(imageLabel);
        menu.PopupMenuForImg(imageLabel);
        imageLabel.repaint();
        imageLabel.validate();
        MainWindow.curShowAllPane.add(imageLabel);
        MainWindow.curShowAllPane.repaint();
        MainWindow.curShowAllPane.validate();

    }

    public void cut(ShowAllPane editPane) {
        editPane = MainWindow.curShowAllPane;
        MainWindow.needDeleted = true;
        MainWindow.ishasEctype = true;

    }

}
