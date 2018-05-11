package com.coolview.ui.panes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class ScalePane extends JPanel implements MouseInputListener{

    private Image image;
    private BufferedImage bufImage;
    private BufferedImage originalBufImage;
    private Graphics2D bufImageG;
    private double scaleX = 1.0;
    private double scaleY = 1.0;
    private int imageWidth,imageHeigth;//图片的长和宽
    private int x1,x2,y1,y2;//x1y1移动之前的坐标，x2y2移动之后的坐标
    private int px,py,qx,qy;//p移动量q移动时的坐标
    private int pWidth,pHeigth;//记录之前的窗口长宽
    private boolean isSlecelt = true;
    private boolean isPlay = false;
    
    public void loadImage(String fileName) {
    	try {
			originalBufImage = ImageIO.read(new File(fileName));
			imageWidth = originalBufImage.getWidth();
			imageHeigth = originalBufImage.getHeight();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("找不到图片");
		}
        MediaTracker mt = new MediaTracker(this);
        addMouseMotionListener(this);
        addMouseListener(this);
        mt.addImage(image, 0);
        try {
            mt.waitForAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        bufImage = originalBufImage;
        pWidth = this.getWidth();
        pHeigth = this.getHeight();
        scaleX = 1;
        scaleY = 1;
        //图片自适应
        while(imageWidth>=pWidth||imageHeigth>=pHeigth) {
        	imageWidth=(int) (imageWidth/1.1);
        	imageHeigth=(int) (imageHeigth/1.1);
        }
        x1 = x2 =  (this.getWidth() - imageWidth) / 2;
        y1 = y2 = (this.getHeight() - imageHeigth) / 2;
        bufImageG = bufImage.createGraphics();
        bufImageG.drawImage(image, 0, 0, this);
        repaint();
    }
    public void paint(Graphics g) {
        super.paintComponent(g);
        if (bufImage != null) {
            Graphics2D g2 = (Graphics2D) g;
            int x,y;
            x = (int) (x2+(this.getWidth()-pWidth)/2-(scaleX-1)*imageWidth/2); 
            y = (int) (y2+(this.getHeight()-pHeigth)/2-(scaleY-1)*imageHeigth/2);
            g2.drawImage(bufImage, x, y, (int)(imageWidth*scaleX), (int)(imageHeigth*scaleY), this);
        }
    }

    public double getScaleX() {
        return scaleX;
    }

    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
    }

    @Override
    public void mousePressed(MouseEvent event) {
    	if(isSlecelt) {
    		qx = event.getX();
    		qy = event.getY();
    		isSlecelt = !isSlecelt;
    	}
    }
    @Override
    public void mouseReleased(MouseEvent event) {
    	if(!isSlecelt) {
    		x1 = x1 + px;
    		y1 = y1 + py;
    	}
    	isSlecelt = true;
    }
    @Override
    public void mouseDragged(MouseEvent event) {
    	if(!isPlay) {
	    	px = event.getX() - qx;
	    	py = event.getY() - qy;
	    	x2 = x1 + px;
	    	y2 = y1 + py;
	    	repaint();
	    }
    }
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
	}
	public boolean isPlay() {
		return isPlay;
	}
	public void setPlay(boolean isPlay) {
		this.isPlay = isPlay;
	}
    


}
