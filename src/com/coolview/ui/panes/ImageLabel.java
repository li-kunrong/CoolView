package com.coolview.ui.panes;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ImageLabel extends JLabel {
    private ImageIcon imageIcon;
    private File imageFile;

    public ImageLabel(ImageIcon imageIcon, File imageFile) {
        super(imageFile.getName(),imageIcon, SwingConstants.CENTER);
        this.imageIcon = imageIcon;
        this.imageFile = imageFile;
        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.BOTTOM);
    }

//    private void addPanel(File imageFile2) {
//        JLabel name = new JLabel(imageFile.getName());
//        this.add(name,BorderLayout.SOUTH);
//        
//    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

}
