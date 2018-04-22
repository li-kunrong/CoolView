package com.coolview.ui.menus;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import com.alee.extended.breadcrumb.BreadcrumbElement;
import com.coolview.logic.BasicFunction;
import com.coolview.logic.EditPhoto;
import com.coolview.logic.FileHelper;
import com.coolview.logic.ViewType;
import com.coolview.ui.Initialize;
import com.coolview.ui.MainWindow;
import com.coolview.ui.listener.PopupListener;
import com.coolview.ui.listener.PopupListenerOfPane;
import com.coolview.ui.panes.ImageLabel;
import com.coolview.ui.panes.ShowAllPane;
import com.coolview.util.Conf;

public class ManagerMenu implements ActionListener {
    private static Conf conf = new Conf();
    private static ActionEnum action;
    private JFrame frame;
    private File editfile;
    private static File pasetFile;
    ShowAllPane editPane = null;
    private ImageLabel imageLabel = null;
    private EditPhoto editPhoto = new EditPhoto();
    private ViewType viewType = new ViewType();

    private ShowAllPane showAllPane;
    private Comparator<File> compareType;

    public ImageLabel getImageLabel() {
        return imageLabel;
    }

    public ManagerMenu(JFrame jFrame) {
        this.frame = jFrame;
    }

    public ManagerMenu(ImageLabel imageLabel) {
        super();
        this.imageLabel = imageLabel;
        this.editfile = imageLabel.getImageFile();
    }

    public ManagerMenu() {

    }

    public JMenuBar createMenu() {
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem item;
        ButtonGroup group;
        JRadioButtonMenuItem radioButtonMenuItem;

        menuBar = new JMenuBar();

        menu = new JMenu("文件(F)");
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);

        item = new JMenuItem("新图像(I)");
        menu.setMnemonic(KeyEvent.VK_I);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        item.setActionCommand("openI");
        item.addActionListener(this);
        menu.add(item);

        item = new JMenuItem("照片信息(U)");
        item.setMnemonic(KeyEvent.VK_U);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.ALT_MASK));
        item.setActionCommand("info");
        item.addActionListener(this);
        menu.add(item);

        item = new JMenuItem("图片路径(P)");
        item.setMnemonic(KeyEvent.VK_P);
        item.setActionCommand("path");
        item.addActionListener(this);
        menu.add(item);

        item = new JMenuItem("退出(C)");
        item.setMnemonic(KeyEvent.VK_C);
        item.setActionCommand("close");
        item.addActionListener(this);
        menu.add(item);

        menu = new JMenu("编辑(E)");
        menu.setMnemonic(KeyEvent.VK_E);
        menuBar.add(menu);

        item = new JMenuItem("剪切(T)");
        item.setMnemonic(KeyEvent.VK_T);
        item.setActionCommand("cut");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        item.addActionListener(this);
        menu.add(item);

        item = new JMenuItem("复制(C)");
        item.setMnemonic(KeyEvent.VK_C);
        item.setActionCommand("copy");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        item.addActionListener(this);
        menu.add(item);

        item = new JMenuItem("粘贴(P)");
        item.setMnemonic(KeyEvent.VK_P);
        item.setActionCommand("paste");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        item.addActionListener(this);
        menu.add(item);

        item = new JMenuItem("删除(D)");
        item.setMnemonic(KeyEvent.VK_D);
        item.setActionCommand("delete");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        item.addActionListener(this);
        menu.add(item);

        item = new JMenuItem("旋转(R)");
        item.setMnemonic(KeyEvent.VK_R);
        item.setActionCommand("rotate");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        item.addActionListener(this);
        menu.add(item);

        item = new JMenuItem("重命名(M)");
        item.setMnemonic(KeyEvent.VK_M);
        item.setActionCommand("rename");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        item.addActionListener(this);
        menu.add(item);

        menu = new JMenu("查看(V)");
        menu.setMnemonic(KeyEvent.VK_V);
        menuBar.add(menu);

        // 查看菜单项
        // [start]子菜单项：显示方式
        submenu = new JMenu("显示方式(S)");
        submenu.setActionCommand("showType");
        menu.add(submenu);

        group = new ButtonGroup();

        radioButtonMenuItem = new JRadioButtonMenuItem("大图标");
        radioButtonMenuItem.setActionCommand("big");
        radioButtonMenuItem.setMnemonic(KeyEvent.VK_B);
        radioButtonMenuItem.addActionListener(this);
        if (MainWindow.showType.equals("big")) {
            radioButtonMenuItem.setSelected(true);
        }
        group.add(radioButtonMenuItem);
        submenu.add(radioButtonMenuItem);

        radioButtonMenuItem = new JRadioButtonMenuItem("中图标");
        radioButtonMenuItem.setActionCommand("middle");
        radioButtonMenuItem.setMnemonic(KeyEvent.VK_M);
        radioButtonMenuItem.addActionListener(this);
        if (MainWindow.showType.equals("middle")) {
            radioButtonMenuItem.setSelected(true);
        }
        group.add(radioButtonMenuItem);
        submenu.add(radioButtonMenuItem);

        radioButtonMenuItem = new JRadioButtonMenuItem("小图标");
        radioButtonMenuItem.setActionCommand("small");
        radioButtonMenuItem.setMnemonic(KeyEvent.VK_S);
        radioButtonMenuItem.addActionListener(this);
        if (MainWindow.showType.equals("small")) {
            radioButtonMenuItem.setSelected(true);
        }
        group.add(radioButtonMenuItem);
        submenu.add(radioButtonMenuItem);

        submenu = new JMenu("排列依据");
        submenu.setActionCommand("sort");
        menu.add(submenu);

        radioButtonMenuItem = new JRadioButtonMenuItem("名字(N)");
        radioButtonMenuItem.setActionCommand("name");
        radioButtonMenuItem.setMnemonic(KeyEvent.VK_N);
        radioButtonMenuItem.addActionListener(this);
        if (MainWindow.sortCmd.equals("name")) {
            radioButtonMenuItem.setSelected(true);
        }
        group.add(radioButtonMenuItem);
        submenu.add(radioButtonMenuItem);

        radioButtonMenuItem = new JRadioButtonMenuItem("名字(N)");
        radioButtonMenuItem.setActionCommand("name");
        radioButtonMenuItem.setMnemonic(KeyEvent.VK_N);
        radioButtonMenuItem.addActionListener(this);
        if (MainWindow.sortCmd.equals("name")) {
            radioButtonMenuItem.setSelected(true);
        }
        group.add(radioButtonMenuItem);
        submenu.add(radioButtonMenuItem);

        radioButtonMenuItem = new JRadioButtonMenuItem("日期(D)");
        radioButtonMenuItem.setActionCommand("date");
        radioButtonMenuItem.setMnemonic(KeyEvent.VK_D);
        radioButtonMenuItem.addActionListener(this);
        if (MainWindow.sortCmd.equals("date")) {
            radioButtonMenuItem.setSelected(true);
        }
        group.add(radioButtonMenuItem);
        submenu.add(radioButtonMenuItem);

        radioButtonMenuItem = new JRadioButtonMenuItem("类型(T)");
        radioButtonMenuItem.setActionCommand("type");
        radioButtonMenuItem.setMnemonic(KeyEvent.VK_T);
        radioButtonMenuItem.addActionListener(this);
        if (MainWindow.sortCmd.equals("type")) {
            radioButtonMenuItem.setSelected(true);
        }
        group.add(radioButtonMenuItem);
        submenu.add(radioButtonMenuItem);

        radioButtonMenuItem = new JRadioButtonMenuItem("大小(S)");
        radioButtonMenuItem.setActionCommand("size");
        radioButtonMenuItem.setMnemonic(KeyEvent.VK_S);
        radioButtonMenuItem.addActionListener(this);
        if (MainWindow.sortCmd.equals("size")) {
            radioButtonMenuItem.setSelected(true);
        }
        group.add(radioButtonMenuItem);
        submenu.add(radioButtonMenuItem);

        submenu.addSeparator();

        group = new ButtonGroup();

        radioButtonMenuItem = new JRadioButtonMenuItem("递增(I)");
        radioButtonMenuItem.setActionCommand("increase");
        radioButtonMenuItem.setMnemonic(KeyEvent.VK_I);
        radioButtonMenuItem.addActionListener(this);
        if (MainWindow.sortCmd.equals("size")) {
            radioButtonMenuItem.setSelected(true);
        }
        group.add(radioButtonMenuItem);
        submenu.add(radioButtonMenuItem);

        radioButtonMenuItem = new JRadioButtonMenuItem("递减(L)");
        radioButtonMenuItem.setActionCommand("decrease");
        radioButtonMenuItem.setMnemonic(KeyEvent.VK_L);
        radioButtonMenuItem.addActionListener(this);
        if (MainWindow.sortCmd.equals("decrease")) {
            radioButtonMenuItem.setSelected(true);
        }
        group.add(radioButtonMenuItem);
        submenu.add(radioButtonMenuItem);

        menu = new JMenu("主题(T)");
        menuBar.add(menu);

        item = new JMenuItem("windows(W)");
        item.setMnemonic(KeyEvent.VK_W);
        item.setActionCommand("windows");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        item.addActionListener(this);
        menu.add(item);

        item = new JMenuItem("Max(A)");
        item.setMnemonic(KeyEvent.VK_A);
        item.setActionCommand("mac");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        item.addActionListener(this);
        menu.add(item);

        item = new JMenuItem("default(G)");
        item.setMnemonic(KeyEvent.VK_G);
        item.setActionCommand("default0");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        item.addActionListener(this);
        menu.add(item);

    

        menu = new JMenu("帮助(H)");
        menu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menu);

        item = new JMenuItem("版本");
        item.setActionCommand("update");
        item.addActionListener(this);
        menu.add(item);

        item = new JMenuItem("作者");
        item.setActionCommand("author");
        item.addActionListener(this);
        menu.add(item);

        return menuBar;
    }

    public void PopupMenuForPane(Component component) {

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem item;
        JMenu submenu;
        JRadioButtonMenuItem radioButtonMenuItem;
        ButtonGroup group;

        item = new JMenuItem("全选(A)");
        item.setMnemonic(KeyEvent.VK_A);
        item.setActionCommand("selectAll");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        item.addActionListener(this);
        popupMenu.add(item);

        submenu = new JMenu("显示方式(S)");
        submenu.setActionCommand("showType");
        popupMenu.add(submenu);

        group = new ButtonGroup();

        radioButtonMenuItem = new JRadioButtonMenuItem("大图标(B)");
        radioButtonMenuItem.setActionCommand("big");
        radioButtonMenuItem.setMnemonic(KeyEvent.VK_B);
        radioButtonMenuItem.addActionListener(this);
        if (MainWindow.showType.equals("big")) {
            radioButtonMenuItem.setSelected(true);
        }
        group.add(radioButtonMenuItem);
        submenu.add(radioButtonMenuItem);

        radioButtonMenuItem = new JRadioButtonMenuItem("中图标(M)");
        radioButtonMenuItem.setActionCommand("middle");
        radioButtonMenuItem.setMnemonic(KeyEvent.VK_M);
        radioButtonMenuItem.addActionListener(this);
        if (MainWindow.showType.equals("middle")) {
            radioButtonMenuItem.setSelected(true);
        }
        group.add(radioButtonMenuItem);
        submenu.add(radioButtonMenuItem);

        radioButtonMenuItem = new JRadioButtonMenuItem("小图标(N)");
        radioButtonMenuItem.setActionCommand("small");
        radioButtonMenuItem.setMnemonic(KeyEvent.VK_N);
        if (MainWindow.showType.equals("small")) {
            radioButtonMenuItem.setSelected(true);
        }
        radioButtonMenuItem.addActionListener(this);
        group.add(radioButtonMenuItem);
        submenu.add(radioButtonMenuItem);
      
        item = new JMenuItem("粘贴(P)");
        item.setMnemonic(KeyEvent.VK_P);
        item.setActionCommand("paste");
        item.addActionListener(this);
        popupMenu.add(item);
        


        item = new JMenuItem("刷新(E)");
        item.setActionCommand("repaint");
        item.setMnemonic(KeyEvent.VK_E);
        item.addActionListener(this);
        popupMenu.add(item);

        submenu = new JMenu("排列依据(O)");
        submenu.setActionCommand("sort");
        popupMenu.add(submenu);

        group = new ButtonGroup();

        radioButtonMenuItem = new JRadioButtonMenuItem("名字(N)");
        radioButtonMenuItem.setActionCommand("name");
        radioButtonMenuItem.setMnemonic(KeyEvent.VK_N);
        radioButtonMenuItem.addActionListener(this);
        if (MainWindow.sortCmd.equals("name")) {
            radioButtonMenuItem.setSelected(true);
        }
        group.add(radioButtonMenuItem);
        submenu.add(radioButtonMenuItem);

        radioButtonMenuItem = new JRadioButtonMenuItem("日期(D)");
        radioButtonMenuItem.setActionCommand("date");
        radioButtonMenuItem.setMnemonic(KeyEvent.VK_D);
        radioButtonMenuItem.addActionListener(this);
        if (MainWindow.sortCmd.equals("date")) {
            radioButtonMenuItem.setSelected(true);
        }
        group.add(radioButtonMenuItem);
        submenu.add(radioButtonMenuItem);

        radioButtonMenuItem = new JRadioButtonMenuItem("类型(T)");
        radioButtonMenuItem.setActionCommand("type");
        radioButtonMenuItem.setMnemonic(KeyEvent.VK_T);
        radioButtonMenuItem.addActionListener(this);
        if (MainWindow.sortCmd.equals("type")) {
            radioButtonMenuItem.setSelected(true);
        }
        group.add(radioButtonMenuItem);
        submenu.add(radioButtonMenuItem);

        radioButtonMenuItem = new JRadioButtonMenuItem("大小(S)");
        radioButtonMenuItem.setActionCommand("size");
        radioButtonMenuItem.setMnemonic(KeyEvent.VK_S);
        radioButtonMenuItem.addActionListener(this);
        if (MainWindow.sortCmd.equals("size")) {
            radioButtonMenuItem.setSelected(true);
        }
        group.add(radioButtonMenuItem);
        submenu.add(radioButtonMenuItem);

        submenu.addSeparator();

        group = new ButtonGroup();

        radioButtonMenuItem = new JRadioButtonMenuItem("递增(I)");
        radioButtonMenuItem.setActionCommand("increase");
        radioButtonMenuItem.setMnemonic(KeyEvent.VK_I);
        radioButtonMenuItem.addActionListener(this);
        if (MainWindow.sortCmd.equals("size")) {
            radioButtonMenuItem.setSelected(true);
        }
        group.add(radioButtonMenuItem);
        submenu.add(radioButtonMenuItem);

        radioButtonMenuItem = new JRadioButtonMenuItem("递减(L)");
        radioButtonMenuItem.setActionCommand("decrease");
        radioButtonMenuItem.setMnemonic(KeyEvent.VK_L);
        radioButtonMenuItem.addActionListener(this);
        if (MainWindow.sortCmd.equals("decrease")) {
            radioButtonMenuItem.setSelected(true);
        }
        group.add(radioButtonMenuItem);
        submenu.add(radioButtonMenuItem);

        MouseListener popupListener = new PopupListenerOfPane(popupMenu);
        component.addMouseListener(popupListener);

    }

    public void PopupMenuForImg(Component component) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem item;
        JMenu menu;

        item = new JMenuItem("查看(V)");
        item.setMnemonic(KeyEvent.VK_V);
        item.setActionCommand("openI");
        item.addActionListener(this);
        popup.add(item);

        item = new JMenuItem("剪切(X)");
        item.setMnemonic(KeyEvent.VK_X);
        item.setActionCommand("cut");
        item.addActionListener(this);
        popup.add(item);

        item = new JMenuItem("复制(C)");
        item.setMnemonic(KeyEvent.VK_C);
        item.setActionCommand("copy");
        item.addActionListener(this);
        popup.add(item);

        item = new JMenuItem("粘贴(P)");
        item.setMnemonic(KeyEvent.VK_P);
        item.setActionCommand("paste");
        item.addActionListener(this);
        popup.add(item);

        popup.addSeparator();

        item = new JMenuItem("删除(D)");
        item.setMnemonic(KeyEvent.VK_D);
        item.setActionCommand("delete");
        item.addActionListener(this);
        popup.add(item);

        item = new JMenuItem("重命名(M)");
        item.setMnemonic(KeyEvent.VK_M);
        item.setActionCommand("rename");
        item.addActionListener(this);
        popup.add(item);

        item = new JMenuItem("图片路径(P)");
        item.setMnemonic(KeyEvent.VK_P);
        item.setActionCommand("path");
        item.addActionListener(this);
        popup.add(item);

        item = new JMenuItem("属性(U)");
        item.setMnemonic(KeyEvent.VK_U);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.ALT_MASK));
        item.setActionCommand("info");
        item.addActionListener(this);
        popup.add(item);

        MouseListener popupListener = new PopupListener(popup, imageLabel, editfile);
        component.addMouseListener(popupListener);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File chooseFile = MainWindow.choosedImgFile;
        ImageLabel choosedImage = MainWindow.choosedImg;
        String command = e.getActionCommand();
        editPane = MainWindow.curShowAllPane;
        ActionEnum value = ActionEnum.valueOf(command);
        switch (value) {
        case openI:
            editPhoto.view();
            break;
        case close:
            frame.dispose();
            break;
        case selectAll:
            
            break;
        case info:
            editPhoto.getInfo(chooseFile, editfile);
            break;
        case path:
            editPhoto.getPath(frame);
            break;
         case cut:
             pasetFile = chooseFile;
             System.out.println(pasetFile);
             imageLabel = choosedImage;
             editPhoto.cut(editPane);
            break;
         case copy:
             pasetFile = chooseFile;
             imageLabel = choosedImage;
//             System.out.println(editfile.getName());
             editPhoto.copy(editPane);
         break;
         case paste:
             System.out.println(pasetFile);
             editPhoto.paste(frame,pasetFile);
//             System.out.println(editfile);
         break;
        case delete:
            editPhoto.delete(chooseFile, choosedImage);
            break;
        case rename:
            editPhoto.rename(frame, editfile, chooseFile);
            break;
        case big:
            viewType.changeToBig();
            break;

        case middle:
            viewType.changeToMiddle();
            break;
        case small:
            viewType.changToSmall();
            break;
        case name:
            viewType.sortByName();
            break;
        case date:
            viewType.sortByDate();
            break;
        case type:
            viewType.sortByType();
            break;

        case size:
            viewType.sortBySize();
            break;
        case increase:
            viewType.sortInIncrease(frame);
            break;
        case decrease:
            viewType.sortInDecrease(frame);
            break;
        case update:
            BasicFunction.getVersion(frame);
            break;
        case author:
            BasicFunction.getAuthor(frame);
            break;
        case repaint:
            new RepaintPane().execute();
            break;
        case default0:
            try {
                FileHelper.changeTheme("系统默认");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                System.out.println("error");
            }
            JOptionPane.showConfirmDialog(null, "需要你重启应用才生效", "提示", JOptionPane.YES_NO_OPTION); 
            break;
        case windows:
            try {
                FileHelper.changeTheme("Nimbus");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                System.out.println("error");
            }
            JOptionPane.showConfirmDialog(null, "需要你重启应用才生效", "提示", JOptionPane.YES_NO_OPTION); 
            break;
            
        case mac:
            try {
                FileHelper.changeTheme("BeautyEye");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                System.out.println("error");
            }
            JOptionPane.showConfirmDialog(null, "需要你重启应用才生效", "提示", JOptionPane.YES_NO_OPTION); 
            break;
        default:
            break;
        }

    }

}
