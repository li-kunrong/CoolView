package com.coolview.ui.frames;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.coolview.ui.frames.ViewFrame.picButton;
import com.coolview.ui.panes.ScalePane;

public class View extends JFrame implements ActionListener{
    private File imgfile;
    private ImageIcon img;
    private Image image;
    private JPanel pane;
    private JPanel tempPane;
    //修改
    private int imgWidth = 480;
    private int imgHeight = 360;
    private int frameWidth = 480 + 60;
    private int frameHeight = 360;
    private int imgChangedWidth;
    private int imgChangedHeight;
    private double changeRate;
    private JLabel imgLabel;
    
    private ScalePane showImagePane = new ScalePane();
    
    private JButton preButton = new JButton("上一张");
    private JButton nextButton = new JButton("下一张");
    private JButton reduceButton = new JButton("缩小");
    private JButton enlargeButton = new JButton("放大");
    private JButton resetButton = new JButton("还原");
    private JButton rotateButton = new JButton("旋转");
    
    private JPanel panel = new JPanel();
    private JPanel panel3 = new JPanel();

    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("文件");
    private JMenuItem saveItem = new JMenuItem("另存为 ...");
    private JMenuItem exitItem = new JMenuItem("退出");

    private JMenu helpMenu = new JMenu("帮助");

    private JMenuItem mnemItem = new JMenuItem("关于");
    private String filename, openPath, dir;
    private int i = 0;
    String[] pics;
    
    
    public View(File imgfile){
        this.imgfile = imgfile;
        img=new ImageIcon(imgfile.getAbsolutePath());
        imgWidth = img.getIconWidth();
        imgHeight = img.getIconHeight();
        image = img.getImage();
        createInfo();
    }

    private void createInfo() {
        Container con = getContentPane();
        con.add(panel, BorderLayout.NORTH);
        con.add(showImagePane, BorderLayout.CENTER);
        con.add(panel3, BorderLayout.SOUTH);
        panel.setLayout(new FlowLayout());

        panel3.add(preButton);
        panel3.add(reduceButton);
        panel3.add(resetButton);
        panel3.add(enlargeButton);
        panel3.add(nextButton);

        setJMenuBar(menuBar);
        menuBar.add(fileMenu);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        menuBar.add(helpMenu);

        helpMenu.add(mnemItem);

        exitItem.addActionListener(this);
        saveItem.addActionListener(this);

        mnemItem.addActionListener(this);

        preButton.addActionListener(new picButton());
        nextButton.addActionListener(new picButton());

        enlargeButton.addActionListener(new picButton());
        reduceButton.addActionListener(new picButton());
        resetButton.addActionListener(new picButton());

        setBounds(60, 100, 600, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        newmethod(imgfile);

    }

    private void newmethod(File imgfile2) {
        filename = imgfile.getName();
        System.out.println(filename);
        openPath = imgfile.getParent();
        System.out.println(openPath);
        File file1 = new File(openPath);
        pics = file1.list();
        for (int j = 0; j < pics.length; j++)
            if (pics[j].equalsIgnoreCase(filename)) {
                i = j;
                break;
            }
        dir = openPath + "/" + filename;
        showImagePane.loadImage(dir);

    }

    public void setFrame(){
        setTitle(imgfile.getName());
        //窗体位置及大小
        Toolkit kit=getToolkit();
        Dimension winSize=kit.getScreenSize();
        setBounds((winSize.width-frameWidth)/2,(winSize.height-frameHeight)/2,frameWidth,frameHeight);
        //setBounds(500,500,frameWidth,frameHeight);
        //this.pack();
        this.setVisible(true);
        this.setMinimumSize(new Dimension(frameWidth,frameHeight));
        
        changeRate = 1.0*frameWidth /imgWidth < 1.0*frameHeight /imgHeight ? 1.0*frameWidth /imgWidth : 1.0*frameHeight /imgHeight ;
        imgChangedWidth = (int)(imgWidth * changeRate);
        imgChangedHeight = (int)(1.0*imgChangedWidth / imgWidth * imgHeight);
        //.getScaledInstance(imgWidth,imgHeight,Image.SCALE_SMOOTH);
        pane = new BackgroundPanel(image.getScaledInstance(imgChangedWidth,imgChangedHeight,Image.SCALE_SMOOTH));
        tempPane = pane;
        this.add(pane);
        this.addComponentListener(new changeImgSizeWithFrame(this));
        this.validate();
    }
    
    class picButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            if (button == preButton) {
                File file = new File(openPath);
                String[] str = file.list();
                str = file.list();
                if (i == 0) {
                    i = str.length - 1;
                } else {
                    i--;
                }
                dir = openPath + "/" + pics[i];
                showImagePane.loadImage(dir);
            } else if (button == nextButton) {
                File file = new File(openPath);
                String[] str = file.list();
                str = file.list();
                if (i == str.length - 1) {
                    i = 0;
                } else {
                    i++;
                }
                dir = openPath + "/" + pics[i];
                showImagePane.loadImage(dir);
            } else if (button == enlargeButton) {
                showImagePane.setScaleX(showImagePane.getScaleX() * 1.25);
                showImagePane.setScaleY(showImagePane.getScaleY() * 1.25);
                showImagePane.applyFilter();
                showImagePane.repaint();
                resetButton.setEnabled(true);
            } else if (button == reduceButton) {
                showImagePane.setScaleX(showImagePane.getScaleX() * 0.8);
                showImagePane.setScaleY(showImagePane.getScaleY() * 0.8);
                showImagePane.applyFilter();
                showImagePane.repaint();
                resetButton.setEnabled(true);
            } else if (button == resetButton) {
                showImagePane.setScaleX(1.0);
                showImagePane.setScaleY(1.0);
                showImagePane.applyFilter();
                showImagePane.repaint();
                resetButton.setEnabled(false);
            }
        }
    }

    //修改：还有一种在JLabel中实现的方法，实验判断哪个显示效果更好
    class BackgroundPanel extends JPanel{
        private Image image;
        public BackgroundPanel(Image image){ 
            this.image=image;
            Dimension size = new Dimension(frameWidth,frameHeight);
            setSize(size);
        }
        @Override
        public void paintComponent(Graphics g) 
        {
//           super.paintComponent(g);
//           Dimension size=this.getParent().getSize();
            //居中设置
            int a = (int)(1.0*(frameWidth - imgChangedWidth)/2);
            int b = (int)(1.0*(frameHeight - imgChangedHeight)/2);
             g.drawImage(image,a,b,imgChangedWidth,imgChangedHeight,this);
        }
    }

    class changeImgSizeWithFrame extends ComponentAdapter{
        View adapter;
        
        public changeImgSizeWithFrame(View adapter){
            this.adapter = adapter;
        }
        @Override
        public void componentResized(ComponentEvent e){
            adapter.changeImgSizeAutoUsePane(adapter.pane);
        }
    }
    public void changeImgSizeAutoUsePane(JPanel pane){
        //if(frameWidth != this.getWidth() && frameHeight != this.getHeight()){
            frameWidth = this.getWidth();
            frameHeight = this.getHeight(); 
            //System.out.println(frameWidth+" "+frameHeight);
            changeRate = 1.0*frameWidth /imgWidth < 1.0*frameHeight /imgHeight ? 1.0*frameWidth /imgWidth : 1.0*frameHeight /imgHeight ;
            //由于imgWidth 和 imgHeight最后会变成int，所以存在一个误差还需继续细化缩放比
            //优化
            //最后的效果中显示Frame中右一块空白的区域
//          System.out.println(changeRate);
//          System.out.println(imgWidth+" "+imgHeight);
            imgChangedWidth = (int)(imgWidth * changeRate);
            imgChangedHeight = (int)(1.0*imgChangedWidth / imgWidth * imgHeight);
            //System.out.println(imgWidth+" "+imgHeight);
        //}
        this.remove(tempPane);
        this.repaint();
        pane = new BackgroundPanel(image);
        pane.setSize(frameWidth ,frameHeight);
        this.add(pane);
        this.repaint();
        this.validate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
    


}

/*
 * 3.3.4 读缩略图 
有的图片格式允许一个（或多个）小的预览图，与主图片一起存储在文件中。这些
“缩略图”对于快速识别图片很有用，不用解码整个图片。

程序可以调用如下代码，探测一张图片有多少张缩略图：
reader.getNumThumbnails(imageIndex);

如果存在缩略图，可以调用如下代码获取：
int thumbailIndex = 0;
BufferedImage bi;
bi = reader.readThumbnail(imageIndex, thumbnailIndex);
 */


//public class BackgroundPanel extends JPanel {
//
//// 固定背景图片，允许这个JPanel可以在图片上添加其他组件
//protected void paintComponent(Graphics g) {
//  //g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);            
//  img=new ImageIcon(imgfile.getAbsolutePath());
//  //img.setImage(img.getImage().getScaledInstance(imgWidth,imgHeight,Image.SCALE_SMOOTH));
//  image = img.getImage();
//  g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
//  
//}
//}
//

//public void changeImgSizeAuto(JLabel imgLabel){
//double changeRate;
//changeRate = 1.0*imgWidth /frameWidth< 1.0*imgHeight /frameHeight ?  1.0*imgWidth /frameWidth : 1.0*imgHeight /frameHeight;
//System.out.println(changeRate);
//this.remove(tempPane);
//this.repaint();
//this.validate();
//imgHeight = (int)(frameHeight * changeRate);
//imgWidth = (int)(frameWidth * changeRate);
//img=new ImageIcon(imgfile.getAbsolutePath());
//////img.setImage(img.getImage().getScaledInstance(imgWidth,imgHeight,Image.SCALE_SMOOTH));
//image = img.getImage();
//pane = new BackgroundPanel(image);
//tempPane = pane;
//this.add(pane);
//this.repaint();
//this.validate();
//}














