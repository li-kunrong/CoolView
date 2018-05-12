package com.coolview.ui.panes;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class ImageLabel extends JLabel implements MouseListener{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ImageIcon imageIcon;
    private File imageFile;
    private boolean isChoice = false;
    public ImageLabel(ImageIcon imageIcon, File imageFile) {
        super(imageFile.getName(),imageIcon, SwingConstants.CENTER);
        this.imageIcon = imageIcon;
        this.imageFile = imageFile;
        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.BOTTOM);
        addMouseListener(this);
    }
    
    public ImageLabel() {
       
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		isChoice = !isChoice;
		if(isChoice == true) {
			ManagerPane.choiceLabel = this;
		}else {
			ManagerPane.choiceLabel = null;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
