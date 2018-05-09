package com.coolview.ui.panes;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.coolview.ui.MainWindow;
import com.coolview.ui.menus.ManagerMenu;

public class ManagerPane extends JPanel  {

    /**
     * 
     */
   
    private JPanel treePanel;

    public ManagerPane() {
       
        setLayout(new BorderLayout());
        MainWindow.sp = new JScrollPane();
        
        MainWindow.sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        MainWindow.curShowAllPane = new ShowAllPane();
//        MainWindow.curShowAllPane.addKeyListener(new PaneKeyListener());
         new ManagerMenu().PopupMenuForPane(MainWindow.curShowAllPane);
        // MainWindow.curShowAllPane.setPreferredSize(new Dimension(480,420));
        MainWindow.sp.getViewport().add(MainWindow.curShowAllPane);
        treePanel = new DirectoryTree();
        treePanel.setFocusable(false);
        MainWindow.curShowAllPane.setFocusable(true);
        JSplitPane middle = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, MainWindow.sp);
        // 设置两个panel的比例大小
        middle.setDividerLocation(300);
        middle.setDividerSize(1);
        middle.setFocusable(true);
//        middle.addKeyListener(new PaneKeyListener());
        // 底下的状态栏
        MainWindow.statusbar = new JLabel();
        MainWindow.statusbar.setText("选中了0张图片");
        JButton play = new JButton("播放");
        play.setVisible(true);
        // JTextField numOfPictures = new JTextField("wait content");
        // numOfPictures.setVisible(true);
        JPanel test = new JPanel();
        test.add(play, BorderLayout.EAST);
        // test.add(numOfPictures,BorderLayout.CENTER);
        test.add(MainWindow.statusbar, BorderLayout.WEST);
        this.add(test);

        add(middle, BorderLayout.CENTER);
        add(test, BorderLayout.SOUTH);
        validate();
    }
    
    

}

