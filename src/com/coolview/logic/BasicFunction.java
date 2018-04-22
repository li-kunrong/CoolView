package com.coolview.logic;

import java.awt.Frame;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributes;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.coolview.bean.VersionSummary;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class BasicFunction {
    
    public static boolean isImage(File file) {
        try {

            ImageInputStream iis = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
            if (!iter.hasNext()) {
                return false;
            }
            iis.close();
            return true;
        } catch (Exception e) {
            System.err.println(e);
        }
        return false;
    }


    public static File[] getDir(File file) {
        return file.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                File f = new File(dir, name);
                if (f.isFile())
                    return false;
                DosFileAttributes attr = null;
                try {
                    attr = Files.readAttributes(Paths.get(f.getAbsolutePath()), DosFileAttributes.class);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, e.toString(), "Warning", JOptionPane.WARNING_MESSAGE);
                    return false;
                }

                if (!f.isHidden() && !attr.isSystem() && !attr.isSymbolicLink()
                        && Files.isReadable(Paths.get(f.getAbsolutePath())))
                    return true;
                else
                    return false;
            }
        });
    }

    public static boolean hasSubDir(File file) {
        File[] files = getDir(file);
        if (files == null)
            return false;
        for (File f : files) {
            if (f.isDirectory())
                return true;
        }
        return false;
    }

    public static File[] getImages(File file) {
        return file.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                File f = new File(dir, name);
                if (f.isDirectory())
                    return false;
                DosFileAttributes attr = null;
                try {
                    attr = Files.readAttributes(Paths.get(f.getAbsolutePath()), DosFileAttributes.class);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, e.toString(), "Warning", JOptionPane.WARNING_MESSAGE);
                    return false;
                }

                if (!attr.isSystem() && isImage(f))
                    return true;
                else
                    return false;
            }
        });
    }


    public static String getNameWithOutExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf(".");
        if(lastIndexOfDot < 0)
            return fileName;
        String tfileName = fileName.substring(0,lastIndexOfDot);
        return tfileName;
    }


    public static String getExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf(".");
        if(lastIndexOfDot < 0)
            return "";//没有拓展名
        String extension = fileName.substring(lastIndexOfDot+1);
        return extension;
    }


    public static String getLocation(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf("\\");
        if(lastIndexOfDot < 0)
            return "unknown";
        String Location = fileName.substring(0,lastIndexOfDot);
        return Location;
    }


    public static File rename(File oldfile, JTextField nameField) {
        File newFile;
        String name = nameField.getText();
        if(name == ""+(char)0 || name.length() == 0){//当输入的内容为空时为字符串""+(char)0，可能是为了与取消时做区分
            JOptionPane.showMessageDialog(null, "文件名不能为空",
                    "提示",javax.swing.JOptionPane.WARNING_MESSAGE);
            return null;
        }
        String regex = "[\\/:[*][?]\"<>|]";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(name);
        if(matcher.find()){
            JOptionPane.showMessageDialog(null, "文件名不能包含下列任何符号之一：\n"+"\\ / : * ? \" < > |",
                    "提示",javax.swing.JOptionPane.WARNING_MESSAGE);
            return null;
        }
        newFile = new File(BasicFunction.getLocation(oldfile.getAbsolutePath())+"\\"+nameField.getText()+ "."+BasicFunction.getExtension(oldfile.getName()));
        if(!oldfile.exists()){
            JOptionPane.showMessageDialog(null, "重命名文件不存在",
                    "提示",javax.swing.JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if(oldfile.equals(newFile)){
            return null;
        }
        if(newFile.exists()){
            JOptionPane.showMessageDialog(null, "文件名已存在","提示",javax.swing.JOptionPane.WARNING_MESSAGE);     
            return null;
        }
        oldfile.renameTo(newFile);
        return newFile;
    }


    public static boolean isLegalName(File editfile, String newFileName, File targetDir) {


        if(newFileName == ""+(char)0 || newFileName.length() == 0){//当输入的内容为空时为字符串""+(char)0，可能是为了与取消时做区分
            JOptionPane.showMessageDialog(null, "文件名不能为空",
                    "提示",javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }
        String regex = "[\\/:[*][?]\"<>|]";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(newFileName);
        if(matcher.find()){
            JOptionPane.showMessageDialog(null, "文件名不能包含下列任何符号之一：\n"+"\\ / : * ? \" < > |",
                    "提示",javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }
        File file = new File(targetDir.getAbsolutePath()+"\\"+newFileName + "."+BasicFunction.getExtension(editfile.getName()));
        System.out.println(file.getAbsolutePath());
        if(file.exists()){
            JOptionPane.showMessageDialog(null, "文件名已存在","提示",javax.swing.JOptionPane.WARNING_MESSAGE);     
            return false;
        }
        return true;
    
    }


    public static File rename(File oldfile, String newName) {
        if(newName == ""+(char)0 || newName.length() == 0){//当输入的内容为空时为字符串""+(char)0，可能是为了与取消时做区分
            JOptionPane.showMessageDialog(null, "文件名不能为空",
                    "提示",javax.swing.JOptionPane.WARNING_MESSAGE);
            return null;
        }
        File newFile;
        String regex = "[\\/:[*][?]\"<>|]";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(newName);
        if(matcher.find()){
            JOptionPane.showMessageDialog(null, "文件名不能包含下列任何符号之一：\n"+"\\ / : * ? \"  < > |",
                    "提示",javax.swing.JOptionPane.WARNING_MESSAGE);
            return null;
        }
        newFile = new File(BasicFunction.getLocation(oldfile.getAbsolutePath()) + "\\" + newName + "."
                + BasicFunction.getExtension(oldfile.getName()));
        if(!oldfile.exists()){
            JOptionPane.showMessageDialog(null, "重命名文件不存在",
                    "提示",javax.swing.JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if(oldfile.equals(newFile)){
            return null;
        }
        if(newFile.exists()){
            JOptionPane.showMessageDialog(null, "文件名已存在","提示",javax.swing.JOptionPane.WARNING_MESSAGE);     
            return null;
        }
        oldfile.renameTo(newFile);
        return newFile;
    }


    public static void getVersion(Frame frame) {
        VersionSummary versionSummary  = new VersionSummary();
        JOptionPane.showMessageDialog(frame, "现在的版本为"+versionSummary.getCurrentVersion(),versionSummary.getCurrentVersion(),
                javax.swing.JOptionPane.PLAIN_MESSAGE, new javax.swing.ImageIcon("image\\无脸男_updateOption.png"));
       
        
    }


    public static void getAuthor(Frame frame) {
        JOptionPane.showMessageDialog(frame,
                "人生若只如初见，\n" + "何事秋风悲画扇。\n\n" + "\n" + "" + "作者：  坤榕\n" + "开始时间：2018-03-12\n"
                        + "结束时间：2018-05-01\n\n",
                "关于", javax.swing.JOptionPane.PLAIN_MESSAGE, new javax.swing.ImageIcon("image\\aboutOption.png"));
        
    }


    public static String pasteImg(File sourceImg, String newFileName, File targetDir) {
        newFileName = targetDir.getAbsolutePath()+"\\"+newFileName + "."+BasicFunction.getExtension(sourceImg.getName());
        try
        {
        if(!sourceImg.exists()){
            JOptionPane.showMessageDialog(null, "粘贴文件"+sourceImg.getName()+"不存在","警告",javax.swing.JOptionPane.WARNING_MESSAGE); 
            return null;
        } 
         Image image =ImageIO.read(sourceImg); 
         int width=image.getWidth(null);
         int height=image.getHeight(null);
         
         BufferedImage imageTag=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
         imageTag.getGraphics().drawImage(image,0,0,width,height,null);
         FileOutputStream out=new FileOutputStream(newFileName);
         JPEGImageEncoder encoder=JPEGCodec.createJPEGEncoder(out);
         encoder.encode(imageTag);
         out.close();
         return newFileName;
        }catch (IOException e) {
         JOptionPane.showMessageDialog(null, "图像复制失败","提示",javax.swing.JOptionPane.WARNING_MESSAGE);    
         e.printStackTrace();
         return null;
        }
    }

}
