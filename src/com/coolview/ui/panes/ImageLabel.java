package com.coolview.ui.panes;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ImageLabel extends JLabel {
    private ImageIcon imageIcon;
    private File imageFile;

    public ImageLabel(ImageIcon imageIcon, File imageFile) {
        super(imageIcon, SwingConstants.CENTER);
        this.imageIcon = imageIcon;
        this.imageFile = imageFile;
        // 待定
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

}
