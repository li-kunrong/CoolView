package ui;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;

import com.apple.eawt.Application;
import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.LogFactory;

import frames.ManagerFrame;
import panes.ImageLabel;
import panes.ManagerPane;
import panes.ShowAllPane;
import util.SystemUtil;

/**
 * Created by rememberber(https://github.com/rememberber) on 2017/6/7.
 */
public class MainWindow {
  

    private static Log logger = LogFactory.get();
    
    public static ArrayList<File> imagesList;
    public static final int BIGLABEL_WIDTH = 216,BIGLABEL_HEIGHT = 162,
                      MIDDLELABEL_WIDTH = 120,MIDDLELABEL_HEIGHT = 90,
                      SMALLLABEL_WIDTH = 60,SMALLLABEL_HEIGHT = 45;
    public static int curImgWidth = 120,curImgHeight = 90;
    public static ShowAllPane curShowAllPane = null;
    public static ImageLabel choosedImg = null;
    public static JScrollPane sp;
    public static DefaultMutableTreeNode currentNode = null;
    public static File choosedImgFile = null;
    public static File curNodePath = null;
    public static boolean increase = true;
    public static String curSortType = "name";
    public static int tipInPaint = 1;
    public static JLabel statusbar;
    public static boolean needDeleted = false; //小写 ：c (cut)
    public static HashMap<File,File> renameFile;
    public static String sortCmd = "name";
    public static String showType = "middle";
    public static boolean sortIncrease = true;

    public MainWindow() {

    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //这里开始调度 UI事件调度线程(EDT)
                createAndShowGUI();
            }
        });

    }
    
    public static void createAndShowGUI() {
        // 初始化主题
        Init.initTheme();
        // 统一设置字体
        Init.initGlobalFont();
        // Windows系统状态栏图标
        ManagerFrame frame = new ManagerFrame(ConstantsUI.APP_NAME);
//        frame.setIconImage(ConstantsUI.IMAGE_ICON);
        // Mac系统Dock图标
        if (SystemUtil.isMacOs()) {
            Application application = Application.getApplication();
            application.setDockIconImage(ConstantsUI.IMAGE_ICON);
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //得到屏幕的尺寸
        frame.setBounds((int) (screenSize.width * 0.1), (int) (screenSize.height * 0.08), (int) (screenSize.width * 0.8),
                (int) (screenSize.height * 0.8));

        Dimension preferSize = new Dimension((int) (screenSize.width * 0.8),
                (int) (screenSize.height * 0.8));
        frame.setPreferredSize(preferSize);
        frame.setFrame();


//        frame.setContentPane(new ManagerPane());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        renameFile = new HashMap<File, File>();
        frame.pack();
        frame.setVisible(true);
    }

}