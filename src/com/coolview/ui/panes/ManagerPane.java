package com.coolview.ui.panes;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.coolview.ui.MainWindow;
import com.coolview.ui.menus.ManagerMenu;




public class ManagerPane extends JPanel{
    private JPanel treePanel;
    
    public ManagerPane() {
        setLayout(new BorderLayout());
        
        MainWindow.sp = new JScrollPane();
        MainWindow.sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        MainWindow.curShowAllPane = new ShowAllPane();
        new ManagerMenu().PopupMenuForPane(MainWindow.curShowAllPane);
//        MainWindow.curShowAllPane.setPreferredSize(new Dimension(480,420));
        MainWindow.sp.getViewport().add(MainWindow.curShowAllPane);
        treePanel = new DirectoryTree();
        JSplitPane middle = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, MainWindow.sp);
        //设置两个panel的比例大小
        middle.setDividerLocation(300);
        middle.setDividerSize(1);
        // 底下的状态栏
        MainWindow.statusbar = new JLabel();
        MainWindow.statusbar.setText("图像路径");

        add(middle, BorderLayout.CENTER);
        add(MainWindow.statusbar, BorderLayout.SOUTH);
        validate();
        
    }

}
