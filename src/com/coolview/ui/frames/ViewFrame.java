package com.coolview.ui.frames;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.coolview.ui.panes.ScalePane;

public class ViewFrame extends JFrame implements ActionListener {
    
//    private JPanel pane;
//    private JPanel tempPane;
    //修改
//    private int frameWidth = 480 + 60;
//    private int frameHeight = 360;
//    private int imgChangedWidth;
//    private int imgChangedHeight;
//    private double changeRate;
	private int imgWidth = 480;
	private int imgHeight = 360;
	private Image image;
	private ImageIcon img;
    JLabel imgLabel;

    private ScalePane showImagePane = new ScalePane();
    private File file;

    private JButton preButton = new JButton("上一张");
    private JButton nextButton = new JButton("下一张");
    private JButton reduceButton = new JButton("缩小");
    private JButton enlargeButton = new JButton("放大");
    private JButton resetButton = new JButton("还原");
    private JButton rotateButton = new JButton("旋转");
    private JButton playButton = new JButton("播放");
    private JButton pauseButton = new JButton("暂停");
    private boolean isPlay = false;
    private Timer playTimer = new Timer();
    private JPanel panel = new JPanel();
    private JPanel panel3 = new JPanel();

    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("文件");
    private JMenuItem saveItem = new JMenuItem("另存为 ...");
    private JMenuItem exitItem = new JMenuItem("退出");

    private JMenu helpMenu = new JMenu("帮助");

    private JMenuItem mnemItem = new JMenuItem("关于");
    
    private int i = 0;
    private String openPath;
    private ArrayList<String> filesName = new ArrayList<>();
    private ArrayList<String> filesAbsPath = new ArrayList<>();
    private File file1;
    private File[] files;
    
    public ViewFrame(File file,boolean isPlay) {
        this.file = file;
        this.isPlay = isPlay;
        this.img=new ImageIcon(file.getAbsolutePath());
        this.imgWidth = img.getIconWidth();
        this.imgHeight = img.getIconHeight();
        this.image = img.getImage();
        createInfo();
    }

    private void createInfo() {
        Container con = getContentPane();
        con.add(panel, BorderLayout.NORTH);
        showImagePane.setLayout(new BorderLayout());
        con.add(showImagePane, BorderLayout.CENTER);
        con.add(panel3, BorderLayout.SOUTH);
        panel.setLayout(new FlowLayout());
        nextButton.setPreferredSize(new Dimension(200, 30));
        preButton.setPreferredSize(new Dimension(200, 30));
        preButton.setContentAreaFilled(false);
        nextButton.setContentAreaFilled(false);
        if(isPlay) {
        	reduceButton.hide();
        	nextButton.hide();
        	enlargeButton.hide();
        	preButton.hide();
        	preButton.hide();
        	resetButton.hide();
        	playButton.hide();
			pauseButton.show();
			playTimer = new Timer();
        	playTimer.schedule(new PlayTask(), 100, 2000);
        	showImagePane.setPlay(true);
        }else {
        	pauseButton.hide();
        }
        resetButton.setEnabled(false);
        showImagePane.add(preButton,BorderLayout.WEST);
        showImagePane.add(nextButton,BorderLayout.EAST);
        //        panel3.add(preButton);
        panel3.add(reduceButton);
        panel3.add(resetButton);
        panel3.add(enlargeButton);
//        panel3.add(nextButton);
        panel3.add(playButton);
        panel3.add(pauseButton);
        setJMenuBar(menuBar);
        menuBar.add(fileMenu);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        menuBar.add(helpMenu);

        helpMenu.add(mnemItem);

        exitItem.addActionListener(this);
        saveItem.addActionListener(this);

        mnemItem.addActionListener(this);

        preButton.addActionListener(new ButtonAction());
        nextButton.addActionListener(new ButtonAction());

        enlargeButton.addActionListener(new ButtonAction());
        reduceButton.addActionListener(new ButtonAction());
        resetButton.addActionListener(new ButtonAction());

        pauseButton.addActionListener(new ButtonAction());
        playButton.addActionListener(new ButtonAction());
        setBounds(60, 100, 900, 600);
        setVisible(true);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newMethod(file);

    }
    private class PlayTask extends TimerTask{
    	@Override
    	public void run() {
    	    showImagePane.loadImage(filesAbsPath.get(i));
            i++;
            if (i == filesAbsPath.size()) {
                i = 0;
            }
            showImagePane.repaint();	
    	}
    }
    private void newMethod(File file) {
        openPath = file.getParent();
        file1 = new File(openPath);
        files = file1.listFiles();
        System.out.println(files.length);
        for(int j = 0;j < files.length; j++) {
        	if(files[j].isFile()&&(files[j].getName().endsWith(".jpg")
        	        ||files[j].getName().endsWith(".png")||files[j].getName().endsWith(".gif"))) {
        		filesName.add(files[j].getName());
        		filesAbsPath.add(files[j].getAbsolutePath());
        	}
        }
        System.out.println(file.getName());
        for(int j = 0; j < filesName.size(); j++) {
        	System.out.println(filesName.get(j));
        	if(filesName.get(j).equals(file.getName())) {
        		i = j;
        		break;
        	}
        }
        showImagePane.loadImage(filesAbsPath.get(i));
    }

    class ButtonAction implements ActionListener {
        @SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            if (button == preButton) {
                resetButton.setEnabled(true);
            	if (i == 0) {
                    i = filesAbsPath.size() - 1;
                } else {
                    i--;
                }
                showImagePane.loadImage(filesAbsPath.get(i));
            } else if (button == nextButton) {
                resetButton.setEnabled(true);
                if (i == filesAbsPath.size() - 1) {
                    i = 0;
                } else {
                    i++;
                }
                showImagePane.loadImage(filesAbsPath.get(i));
                } else if (button == enlargeButton) {
                showImagePane.setScaleX(showImagePane.getScaleX() * 1.25);
                showImagePane.setScaleY(showImagePane.getScaleY() * 1.25);
                showImagePane.repaint();
                resetButton.setEnabled(false);
            } else if (button == reduceButton) {
                showImagePane.setScaleX(showImagePane.getScaleX() * 0.8);
                showImagePane.setScaleY(showImagePane.getScaleY() * 0.8);
                showImagePane.repaint();
                resetButton.setEnabled(false);
            } else if (button == resetButton) {
                showImagePane.loadImage(filesAbsPath.get(i));
                showImagePane.repaint();
                resetButton.setEnabled(false);
            } else if (button == playButton){
            	playTimer = new Timer();
            	playTimer.schedule(new PlayTask(), 2000, 2000);
            	showImagePane.setPlay(true);
            	reduceButton.hide();
            	nextButton.hide();
            	enlargeButton.hide();
            	preButton.hide();
            	preButton.hide();
            	resetButton.hide();
            	playButton.hide();
				pauseButton.show();
			} else if(button == pauseButton) {
				playTimer.cancel();
				showImagePane.setPlay(false);
				reduceButton.show();
				nextButton.show();
				resetButton.show();
				preButton.show();
				enlargeButton.show();
				playButton.show();
				pauseButton.hide();
			}
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JMenuItem click = (JMenuItem) e.getSource();

        if (click == exitItem) {
            dispose();
        }

        if (click == mnemItem) {
            mnem();
        }
    }

    private void mnem() {

        JOptionPane.showMessageDialog(mnemItem, "kunrong");
    }
  

    public static void main(String[] args) {
        File file = new File("D:\\照片\\1.jpg");
        new ViewFrame(file,true);
    }

}
