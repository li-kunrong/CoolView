package com.coolview.ui;

import java.awt.Image;
import java.awt.Toolkit;


public class ConstantsUI {


    /**
     * 软件名称,版本
     */
    public final static String APP_NAME = "CoolView";
    public final static String APP_VERSION = "v_0.0.1";

    /**
     * 主窗口图标
     */
    public final static Image IMAGE_ICON = Toolkit.getDefaultToolkit()
            .getImage(MainWindow.class.getResource("/icon/drive.png"));

    


}
