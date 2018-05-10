package com.coolview.ui.listener;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;

public class NewMouseListener implements MouseInputListener{
    private int x1,y1;//鼠标点击的起始位置
    private int x,y;//矩形左上角
    private int width,height;//矩形长宽
    private int CX,CY;//查找该点是否有组件
    
    ArrayList<Component> components = new ArrayList<>();
    private ImageIcon image;
    private JLabel jLabel;
    private ArrayList<JLabel> jLabels = new ArrayList<>();//保存已经选中的label

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
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

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

}
