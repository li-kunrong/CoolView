package com.coolview.ui;

import java.awt.Font;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.alee.laf.WebLookAndFeel;
import com.coolview.logic.FileHelper;
import com.coolview.util.Conf;
import com.sun.java.swing.plaf.motif.MotifLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

public class Initialize {
    private static Conf con = new Conf();

    public static void initTheme() {
        String theme = null;
        try {
            theme = FileHelper.read("./data");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        // int num = Integer.parseInt(theme);
        try {
            switch (theme) {
            case "BeautyEye":
                BeautyEyeLNFHelper.launchBeautyEyeLNF();
                UIManager.put("RootPane.setupButtonVisible", false);
                break;
            case "weblaf":
                UIManager.setLookAndFeel(new WebLookAndFeel());
                break;
            case "系统默认":
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                break;
            case "Windows":
                UIManager.setLookAndFeel(WindowsLookAndFeel.class.getName());
                break;
            case "Nimbus":
                UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
                break;
            case "Metal":
                UIManager.setLookAndFeel(MetalLookAndFeel.class.getName());
                break;
            case "Motif":
            default:
                UIManager.setLookAndFeel(MotifLookAndFeel.class.getName());

            }
        } catch (Exception e) {
            System.out.println("error");
        }

    }
    public static void initGlobalFont() {
        Font fnt = new Font("Microsoft YaHei UI", Font.PLAIN, 18);
        FontUIResource fontRes = new FontUIResource(fnt);
        for (Enumeration keys = UIManager.getDefaults().keys(); keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource)
                UIManager.put(key, fontRes);
        }
    }

}
