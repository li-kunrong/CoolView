package com.coolview.logic;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.coolview.ui.MainWindow;
import com.coolview.ui.frames.InfoFrame;
import com.coolview.ui.menus.ManagerMenu;
import com.coolview.ui.menus.RepaintPane;
import com.coolview.ui.panes.ImageLabel;
import com.coolview.ui.panes.ShowAllPane;

public class EditPhoto {
    
    public static Image TextToImage(String text){
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Arial", Font.PLAIN, 48);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, 0, fm.getAscent());
        g2d.dispose();
        return img;
    }
    
    public void selectAll(File nodePath){
        if (MainWindow.imagesList == null || MainWindow.imagesList.size() == 0) {
            return;
        }
        MainWindow.isSelectAll = true;
        new RepaintPane().execute();
    }

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
        if (!MainWindow.isSelectAll){
            if (chooseFile == null)
                return;
            chooseFile.delete();

            MainWindow.curShowAllPane.remove(choosedImage);
            MainWindow.curShowAllPane.repaint();
            MainWindow.curShowAllPane.validate();

            MainWindow.imagesList.remove(chooseFile);
            MainWindow.choosedImgFile = null;
            MainWindow.choosedImg = null;
            return;
        }
        
        File[] imageFile = BasicFunction.getImages(MainWindow.curNodePath);
        for (int i = 0;i < imageFile.length; i++) {
            
            MainWindow.imagesList.remove(imageFile[i]);
            imageFile[i].delete();
        }
//        for (File f:MainWindow.imagesList){
//            f.delete();
//            MainWindow.imagesList.remove(f);
//        }
        MainWindow.curShowAllPane.repaint();
        MainWindow.curShowAllPane.validate();
        new RepaintPane().execute();
        
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
        if (!MainWindow.isSelectAll){
            editPane = MainWindow.curShowAllPane;
            MainWindow.needDeleted = false;
            MainWindow.ishasEctype = true; 
        }
        
        
       

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
