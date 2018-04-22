package com.coolview.ui.menus;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultMutableTreeNode;

import com.coolview.ui.MainWindow;
import com.coolview.ui.panes.ImageLabel;
import com.coolview.ui.panes.ShowAllPane;


public class RepaintPane extends SwingWorker<Void, ImageLabel>{
    private ShowAllPane showAllPane;
    private DefaultMutableTreeNode node;
    private ImageIcon img;
    private ImageLabel imgLabel;
    private int imgWidth;
    private int imgHeight;
    private double changeRate;
    private int imgChangedWidth;
    private int imgChangedHeight;
    
    
    public RepaintPane(){
        showAllPane = new ShowAllPane();
        new ManagerMenu().PopupMenuForPane(showAllPane);
        showAllPane.setPreferredSize(MainWindow.curShowAllPane.getSize());    
        node = MainWindow.currentNode;
    }
    public RepaintPane(ShowAllPane showAllPane){
        this.showAllPane = showAllPane;
    }
    public RepaintPane(ShowAllPane showAllPane,DefaultMutableTreeNode node) {
        this.showAllPane = showAllPane;
//      this.showAllPane.setPreferredSize(new Dimension(420,420));
        this.node = node;
    }
    public void setShowAllPane(ShowAllPane showAllPane){
        this.showAllPane = showAllPane;
    }
    
    public Void doInBackground() {
        MainWindow.sp.remove(MainWindow.curShowAllPane);
        MainWindow.sp.getViewport().add(showAllPane);
        MainWindow.sp.revalidate();
        MainWindow.sp.updateUI();
        MainWindow.curShowAllPane = showAllPane;
        int n = MainWindow.imagesList.size();
        System.out.println(n);
        
        int index = 0;
        for(index = 0 ;index < n;index++){

            if(node != MainWindow.currentNode){
                return null;
            }
            
            File imgfile = MainWindow.imagesList.get(index);
            img=new ImageIcon(imgfile.getAbsolutePath());
            imgWidth = img.getIconWidth();
            imgHeight = img.getIconHeight();
            
            changeRate = 1.0*MainWindow.curImgWidth /imgWidth < 1.0*MainWindow.curImgHeight /imgHeight ? 
                    1.0*MainWindow.curImgWidth /imgWidth : 1.0*MainWindow.curImgHeight /imgHeight ;
            imgChangedWidth = (int)(imgWidth * changeRate);
            imgChangedHeight = (int)(1.0*imgChangedWidth / imgWidth * imgHeight);
            img.setImage(img.getImage().getScaledInstance(imgChangedWidth,imgChangedHeight,Image.SCALE_SMOOTH));
            imgLabel = new ImageLabel(img,imgfile);
            new ManagerMenu(imgLabel).PopupMenuForImg(imgLabel);
            publish(imgLabel);
        }
        return null;
    }
    @Override
    protected void process(java.util.List<ImageLabel> chunks) {
        int n = chunks.size();
        for(int i = 0;i<chunks.size();i++){
            showAllPane.add(chunks.get(i));
            showAllPane.updateUI();
            System.out.println("adding");
        }
    }

    @Override
    public void done() {
        System.out.println("done");
    }
}
