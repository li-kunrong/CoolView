package com.coolview.ui.panes;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.junit.validator.PublicClassValidator;

public class ImageLabel extends JLabel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ImageIcon imageIcon;
    private File imageFile;

    public ImageLabel(ImageIcon imageIcon, File imageFile) {
        super(imageFile.getName(),imageIcon, SwingConstants.CENTER);
        this.imageIcon = imageIcon;
        this.imageFile = imageFile;
        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.BOTTOM);
    }
    
    public ImageLabel() {
       
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

}
