package com.coolview.ui.frames;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.coolview.ui.menus.ManagerMenu;
import com.coolview.ui.panes.ManagerPane;




public class ManagerFrame extends JFrame{
    public ManagerFrame(String name) {
        super(name);
       
    }
    
    public void setFrame() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.createImage("image\\logo_eye.png");
        this.setIconImage(image);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        //菜单项
        setJMenuBar((new ManagerMenu(this)).createMenu());
        // 设置窗体的内容区
        setContentPane(new ManagerPane());
        
//        Toolkit kit = getToolkit();
        //根据屏幕的大小生成最适合大小的窗口
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //得到屏幕的尺寸
        setBounds((int) (screenSize.width * 0.1), (int) (screenSize.height * 0.08), (int) (screenSize.width * 0.8),
                (int) (screenSize.height * 0.8));

        Dimension preferSize = new Dimension((int) (screenSize.width * 0.8),
                (int) (screenSize.height * 0.8));
        setPreferredSize(preferSize);
        setVisible(true);   
        
    }

}
