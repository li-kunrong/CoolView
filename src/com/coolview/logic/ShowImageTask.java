package com.coolview.logic;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultMutableTreeNode;

import com.coolview.ui.MainWindow;
import com.coolview.ui.menus.ManagerMenu;
import com.coolview.ui.panes.ImageLabel;
import com.coolview.ui.panes.ShowAllPane;

public class ShowImageTask extends SwingWorker<Void, ImageLabel> {
    private ShowAllPane showAllPane;
    private DefaultMutableTreeNode node;
    private ImageIcon img;
    private Image text;
    private ImageLabel imgLabel;
    private int count = 0;
    private double changeRate;
    private int imgWidth;
    private int imgHeight;
    private int imgChangedWidth;
    private int imgChangedHeight;

    public ShowImageTask() {
        showAllPane = null;
    }

    public ShowImageTask(ShowAllPane showAllPane, DefaultMutableTreeNode node) {
        this.showAllPane = showAllPane;
        this.showAllPane.setPreferredSize(new Dimension(420, 420));
        this.node = node;
    }

    public void setShowAllPane(ShowAllPane showAllPane) {
        this.showAllPane = showAllPane;
    }

    @Override
    public Void doInBackground() {
        //在后台中运行，所有的耗时操作都应该在这里运行
        MainWindow.sp.remove(MainWindow.curShowAllPane);        
        MainWindow.sp.getViewport().add(showAllPane);
        MainWindow.sp.revalidate();
        MainWindow.sp.updateUI();
        MainWindow.curShowAllPane = showAllPane;
        MainWindow.labelList = new ArrayList<>();

        int n = MainWindow.imagesList.size();
        int index = 0;
        for(index = 0 ;index < n;index++){
            if(node != MainWindow.currentNode){
                return null;
            }
            //一下代码是将存在imgesList中的图片文件生成一个图片label，并抛出到process中
            File imgfile = MainWindow.imagesList.get(index);
            img=new ImageIcon(imgfile.getAbsolutePath());
//            Image temp = MergedIcon.iconToBufferedImage(img);
//            text =  EditPhoto.TextToImage(imgfile.getName());
//            img = new ImageIcon(new MergedIcon(temp, text).getM_buffer());
//            img = MergeIcon(img, text);
            imgWidth = img.getIconWidth();
            imgHeight = img.getIconHeight();
            
            changeRate = 1.0*MainWindow.curImgWidth /imgWidth < 1.0*MainWindow.curImgHeight /imgHeight ? 
                    1.0*MainWindow.curImgWidth /imgWidth : 1.0*MainWindow.curImgHeight /imgHeight ;
            imgChangedWidth = (int)(imgWidth * changeRate);
            imgChangedHeight = (int)(1.0*imgChangedWidth / imgWidth * imgHeight);
            img.setImage(img.getImage().getScaledInstance(imgChangedWidth,imgChangedHeight,Image.SCALE_SMOOTH));
            imgLabel = new ImageLabel(img,imgfile);
            MainWindow.labelList.add(imgLabel);
            
         
            //添加鼠标事件
            new ManagerMenu(imgLabel).PopupMenuForImg(imgLabel);
            publish(imgLabel); //会将数据抛出到process中
        }
        return null;
    }

    

    @Override
    protected void process(List<ImageLabel> chunks) { // 注意这里的参数是一个List
        // 这里好像也是运行在事件调度线程中
        // 这段代码是一个例子，对应的参数类型是SwingWork<Void, ImageLabel>中的第二个参数，publish也是。
        int n = chunks.size();
        for (int i = 0; i < chunks.size(); i++) { // 这里是一个循环遍历参数的List
            count++;
            if (count % 5 == 0) {
                showAllPane.setPreferredSize(new Dimension(showAllPane.getWidth(), showAllPane.getHeight() + 120));
            }
            showAllPane.add(chunks.get(i));
            showAllPane.updateUI();
            // showAllPane.repaint();
            // showAllPane.validate();
            // System.out.println("adding");
        }
    }

    @Override
    public void done() {
        // 当后台程序运行结束后就会运行这段代码
        if (node != MainWindow.currentNode) {
            // 运行在事件调度线程中
            System.out.println("done");
        }
    }
}
