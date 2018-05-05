package com.coolview.ui.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import com.coolview.logic.BasicFunction;
import com.coolview.ui.MainWindow;
import com.coolview.ui.menus.ManagerMenu;
import com.coolview.ui.panes.ImageLabel;



public class InfoFrame extends JFrame{

    private Box baseBox, boxAttribute, boxInfo;
    private JPanel buttonPane;
    private JPanel infoPane;
    private JButton sureBt, cancelBt;
    private JTextField info;
    private JTextField nameField;
    private ImageLabel imgLabel;
    private File curFile;
    private File editfile;
    private JFrame frame;
    private ImageIcon img;
    private ImageLabel curImgLabel;
    private int infoWidth = 18;
    private int Strut = 10;
    public DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public InfoFrame(File editfile) {
        this.editfile = editfile;
        setTitle("图像信息");
        curFile = MainWindow.choosedImgFile;
        frame = this;
        img = new ImageIcon(curFile.getAbsolutePath());
        imgLabel = MainWindow.choosedImg;
        curImgLabel = imgLabel;
    }

    public void createAttFrame() {

        // 图像属性
        boxAttribute = Box.createVerticalBox();
        boxAttribute.add(new JLabel("名称："));
        boxAttribute.add(Box.createVerticalStrut(Strut));
        boxAttribute.add(new JLabel("格式："));
        boxAttribute.add(Box.createVerticalStrut(Strut));
        boxAttribute.add(new JLabel("图片尺寸："));
        boxAttribute.add(Box.createVerticalStrut(Strut));
        boxAttribute.add(new JLabel("位置："));
        boxAttribute.add(Box.createVerticalStrut(Strut));
        boxAttribute.add(new JLabel("文件大小："));
        boxAttribute.add(Box.createVerticalStrut(Strut));
        boxAttribute.add(new JLabel("创建时间："));

        // 图像信息
        boxInfo = Box.createVerticalBox();

        // 名称
        nameField = new JTextField(infoWidth);
        nameField.setText(BasicFunction.getNameWithOutExtension(curFile.getName()));
        nameField.addKeyListener(new EnterKeyListener(curFile, nameField, imgLabel));
        boxInfo.add(nameField);
        boxInfo.add(Box.createVerticalStrut(Strut));
        // 格式
        info = new JTextField(infoWidth);
        info.setText(BasicFunction.getExtension(curFile.getName().toUpperCase()));
        info.setEditable(false);
        info.setBorder(null);
        boxInfo.add(info);
        boxInfo.add(Box.createVerticalStrut(Strut));
        // 尺寸
        info = new JTextField(infoWidth);
        info.setText(img.getIconWidth() + "x" + img.getIconHeight() + "像素(宽x高)");
        info.setEditable(false);
        info.setBorder(null);
        boxInfo.add(info);
        boxInfo.add(Box.createVerticalStrut(Strut));
        // 位置
        info = new JTextField(infoWidth);
        info.setText(BasicFunction.getLocation(curFile.getAbsolutePath()));
        boxInfo.add(info);
        boxInfo.add(Box.createVerticalStrut(Strut));
        // 大小
        info = new JTextField(infoWidth);
        info.setText("" + curFile.length());
        info.setEditable(false);
        info.setBorder(null);
        boxInfo.add(info);
        boxInfo.add(Box.createVerticalStrut(Strut));
        // 时间
        info = new JTextField(infoWidth);
        BasicFileAttributes bfa = null;
        try {
            bfa = Files.readAttributes(Paths.get(curFile.getAbsolutePath()), DosFileAttributes.class);
            info.setText("" + df.format(new Date(bfa.creationTime().toMillis())));
        } catch (IOException e) {
            System.out.println("获取图像创建时间出错");
            info.setText("unknown");
        }
        info.setEditable(false);
        info.setBorder(null);
        boxInfo.add(info);
        // 创建baseBox
        baseBox = Box.createHorizontalBox();

        baseBox.add(boxAttribute);
        baseBox.add(Box.createHorizontalStrut(10));
        baseBox.add(boxInfo);

        infoPane = new JPanel();
        // infoPane.setLayout(new FlowLayout());
        infoPane.add(baseBox);

        setLayout(new BorderLayout());
        add(new JPanel(), BorderLayout.NORTH);
        add(infoPane, BorderLayout.CENTER);
        // 按键
        sureBt = new JButton("确定");
        sureBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File temp = BasicFunction.rename(curFile, nameField);
                // 重命名成功
                if (temp != null) {
                    if (MainWindow.choosedImgFile == curFile) {
                        MainWindow.choosedImgFile = temp;
                        MainWindow.choosedImg.setImageFile(MainWindow.choosedImgFile);
                    }
                    MainWindow.renameFile.put(editfile, temp);
                    MainWindow.imagesList.remove(editfile);
                    MainWindow.imagesList.add(temp);
                    editfile = temp;

                    curImgLabel.setImageFile(temp);
                    frame.dispose();
                }
            }
        });
        cancelBt = new JButton("取消");
        cancelBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        buttonPane = new JPanel();
        FlowLayout flow = new FlowLayout();
        flow.setAlignment(FlowLayout.RIGHT);
        buttonPane.setLayout(flow);
        buttonPane.add(sureBt);
        buttonPane.add(cancelBt);
        buttonPane.setBorder(new MatteBorder(1, 0, 0, 0, new Color(202, 202, 202)));
        add(buttonPane, BorderLayout.SOUTH);

        validate();
        setBounds(500, 500, 500, 463);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        System.out.println("end");
    }

    class EnterKeyListener implements KeyListener {
        File oldfile;
        JTextField nameField;

        public EnterKeyListener(File oldfile, JTextField nameField, ImageLabel imgLabel) {
            this.oldfile = oldfile;
            this.nameField = nameField;
        }

        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                File temp = BasicFunction.rename(curFile, nameField);
                // 重命名成功
                if (temp != null) {
                    if (MainWindow.choosedImgFile == curFile) {

                        MainWindow.choosedImgFile = temp;
                        MainWindow.choosedImg.setImageFile(MainWindow.choosedImgFile);
                    }
                    MainWindow.renameFile.put(editfile, temp);
                    MainWindow.imagesList.remove(editfile);
                    MainWindow.imagesList.add(temp);

                    editfile = temp;
                    curImgLabel.setImageFile(temp);
                    frame.dispose();
                }
                // 失败不做任何操作
            }
        }

        @Override
        public void keyReleased(java.awt.event.KeyEvent e) {
        }

        @Override
        public void keyTyped(java.awt.event.KeyEvent e) {
        }
    }

    

}
