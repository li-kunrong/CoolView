package com.coolview.ui.panes;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.coolview.ui.MainWindow;

public class ShowAllPane extends JPanel implements MouseInputListener{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x1, y1;// 鼠标点击的起始位置
    private int x, y;// 矩形左上角
    private int width, height;// 矩形长宽
    private int CX, CY;// 查找该点是否有组件
    JButton jButton = new JButton("test");
    ArrayList<Component> components = new ArrayList<>();
    private ArrayList<ImageLabel> imageLabels = new ArrayList<>();// 保存已经选中的label
    private ImageIcon image;
    private ImageLabel choiceLabel;

    public ShowAllPane() {
        super();
        this.setFocusable(true);
        FlowLayout flow = new FlowLayout();
        flow.setAlignment(FlowLayout.LEFT);
        setLayout(flow);
        setBackground(new Color(255, 255, 255));
        setSize(this.getSize());
        setOpaque(true);
        setVisible(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        validate();
    }

    public ShowAllPane(String fileName, String filePath) {
        super();
        FlowLayout flow = new FlowLayout();
        flow.setAlignment(FlowLayout.LEFT);
        // setLayout(flow);
        setBackground(new Color(255, 255, 255));
        setSize(this.getSize());
        setOpaque(true);
        setVisible(true);

        addMouseListener(this);
        addMouseMotionListener(this);
        image = new ImageIcon(filePath);
        // jLabel = new JLabel(fileName,image,SwingConstants.CENTER);
        // this.add(jLabel);
        // jLabel = new JLabel(fileName,image,SwingConstants.CENTER);
        // this.add(jLabel);
        // jLabel = new JLabel(fileName,image,SwingConstants.CENTER);
        // this.add(jLabel);
        // jLabel = new JLabel(fileName,image,SwingConstants.CENTER);
        // this.add(jLabel);
        // for(int i = 0; i < 10; i++) {
        // jButton = new JButton("hhh");
        // this.add(jButton);
        // }
        validate();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(120, 100, 250));
        g.drawRect(x, y, width, height);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getX() <= x1 && e.getY() <= y1) {
            width = x1 - e.getX();
            height = y1 - e.getY();
            x = x1 - width;
            y = y1 - height;
        } else if (e.getX() > x1 && e.getY() < y1) {
            width = e.getX() - x1;
            height = y1 - e.getY();
            x = x1;
            y = y1 - height;
        } else if (e.getX() <= x1 && e.getY() >= y1) {
            width = x1 - e.getX();
            height = e.getY() - y1;
            x = x1 - width;
            y = y1;
        } else if (e.getX() > x1 && e.getY() > y1) {
            width = e.getX() - x1;
            height = e.getY() - y1;
            x = x1;
            y = y1;
        }
        repaint();

    }

    @Override
    public void mousePressed(MouseEvent e) {

        x1 = e.getX();
        y1 = e.getY();
        components.clear();
        for (int i = 0; i < imageLabels.size(); i++) {
            imageLabels.get(i).setBorder(null);
        }
        imageLabels.clear();
        repaint();
        MainWindow.statusbar.setText("选中了" + 0 + "张图片");

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        CX = x;
        CY = y;
        Component component;
        // 矩形中查找组件
        for (; CX < x + width; CX += 10) {
            for (CY = y; CY < y + height; CY += 10) {
                component = this.getComponentAt(CX, CY);
                if (component != null && component instanceof ImageLabel) {
                    if (components.indexOf(component) == -1) {
                        components.add(this.getComponentAt(CX, CY));
                    }
                }
            }
        }
        for (int i = 0; i < components.size(); i++) {
            imageLabels.add((ImageLabel) components.get(i));
        }
        if (components.size()!=0){//没有组件才新建list
            MainWindow.selectList = new ArrayList<>();
        }
       
        for (int i = 0; i < imageLabels.size(); i++) {
            imageLabels.get(i).setBorder(BorderFactory.createLineBorder(new Color(163, 230, 249), 2));
            System.out.println(imageLabels.get(i).getText());
            // ImageIcon temp = (ImageIcon) imageLabels.get(i);

            MainWindow.selectList.add(imageLabels.get(i).getImageFile());
        }
        System.out.println(imageLabels.size());
        MainWindow.statusbar.setText("选中了" + imageLabels.size() + "张图片");
        System.out.println("selectList的的大小" + MainWindow.selectList.size());
        width = 0;
        height = 0;
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("test");
        jFrame.add(new ShowAllPane("1111", "D:\\1.jpg"));
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(500, 500);
    }


	public ImageLabel getChoiceLabel() {
		return choiceLabel;
	}

	public void setChoiceLabel(ImageLabel choiceLabel) {
		this.choiceLabel = choiceLabel;
	}

}
